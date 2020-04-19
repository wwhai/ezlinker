package com.ezlinker.app.modules.device.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.ezlinker.app.modules.feature.model.Feature;
import com.ezlinker.app.modules.feature.service.IFeatureService;
import com.ezlinker.app.modules.module.model.Module;
import com.ezlinker.app.modules.module.model.StreamIntegration;
import com.ezlinker.app.modules.module.pojo.DataArea;
import com.ezlinker.app.modules.module.service.IModuleService;
import com.ezlinker.app.modules.module.service.IStreamIntegrationService;
import com.ezlinker.app.modules.moduletemplate.model.ModuleTemplate;
import com.ezlinker.app.modules.moduletemplate.service.IModuleTemplateService;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;
import com.ezlinker.app.modules.mqtttopic.service.IMqttTopicService;
import com.ezlinker.app.modules.product.model.Product;
import com.ezlinker.app.modules.product.service.IProductService;
import com.ezlinker.app.modules.systemconfig.service.IDeviceProtocolConfigService;
import com.ezlinker.app.modules.tag.model.Tag;
import com.ezlinker.app.modules.tag.service.ITagService;
import com.ezlinker.app.utils.IDKeyUtil;
import com.ezlinker.app.utils.ModuleTokenUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 实际设备，是产品的一个实例。
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/devices")
public class DeviceController extends CurdController<Device> {
    // 发布权限
    private static final int TOPIC_PUB = 1;
    // 订阅权限
    private static final int TOPIC_SUB = 2;

    @Resource
    ITagService iTagService;
    @Resource
    IFeatureService iFeatureService;
    @Resource
    IProductService iProductService;
    @Resource
    IDeviceService iDeviceService;
    @Resource
    IModuleService iModuleService;
    @Resource
    IMqttTopicService iMqttTopicService;
    @Resource
    IModuleTemplateService iModuleTemplateService;
    @Resource
    IDeviceProtocolConfigService iDeviceProtocolConfigService;
    @Resource
    IStreamIntegrationService iStreamIntegrationService;
    @Resource
    IDKeyUtil idKeyUtil;

    public DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 获取当前支持的协议的类型，目前暂时支持4种
     *
     * @return
     */
    @GetMapping("/protocols")
    public R getProtocols() {
        return data(iDeviceProtocolConfigService.list());
    }

    /**
     * 设备详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        Device device = iDeviceService.getById(id);
        if (device == null) {
            throw new BizException("Device not exist", "设备不存在");
        }

        addTags(device);
        addModules(device);
        return data(device);
    }

    /**
     * 条件检索
     *
     * @param current
     * @param size
     * @param name
     * @param type
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam Long productId,
            @RequestParam Integer current,
            @RequestParam Integer size,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String sn,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String model) {

        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(projectId != null, "project_id", projectId);
        queryWrapper.eq(productId != null, "product_id", productId);
        queryWrapper.eq(sn != null, "sn", sn);
        queryWrapper.eq(model != null, "model", model);
        queryWrapper.eq(type != null, "type", type);
        queryWrapper.like(name != null, "name", name);
        queryWrapper.like(industry != null, "industry", industry);
        queryWrapper.orderByDesc("create_time");

        IPage<Device> devicePage = iDeviceService.page(new Page<>(current, size), queryWrapper);
        for (Device device : devicePage.getRecords()) {
            addTags(device);
            addModules(device);
        }

        return data(devicePage);
    }

    private void addModules(Device device) {
        List<Module> modules = iModuleService.list(new QueryWrapper<Module>().eq("device_id", device.getId()));
        device.setModules(modules);
    }

    /**
     * 增加Tag
     *
     * @param device
     */
    private void addTags(Device device) {
        List<Tag> tagList = iTagService.list(new QueryWrapper<Tag>().eq("link_id", device.getProductId()));
        Set<String> tags = new HashSet<>();
        for (Tag tag : tagList) {
            tags.add(tag.getName());
        }
        device.setTags(tags);
    }

    /**
     * 创建设备
     *
     * @param form
     * @return
     * @throws XException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    protected R add(@RequestBody @Valid Device form) throws XException {

        /**
         * 先找到设备的产品
         */
        Product product = iProductService.getById(form.getProductId());
        checkModelNull(product);

        /**
         * 生成MQTT的相关参数
         */
        String username = SecureUtil.md5(IDKeyUtil.generateId().toString());
        String clientId = SecureUtil.md5(username);
        String password = SecureUtil.md5(clientId);
        form.setParameters(product.getParameters())
                .setProtocol(product.getProtocol())
                .setUsername(username)
                .setPassword(password)
                .setClientId(clientId)
                .setProductId(product.getId())
                .setProjectId(product.getProjectId())
                .setLogo(product.getLogo())
                .setSn("SN" + idKeyUtil.nextId());
        // 保存设备

        iDeviceService.save(form);


        // 从设计好的产品模板里面拿数据
        // 先查到当时设计的产品下的所有模块的模板
        // 然后用模板模板来构建具体的实际模块
        List<ModuleTemplate> moduleTemplates = iModuleTemplateService.list(new QueryWrapper<ModuleTemplate>().eq("product_id", product.getId()));


        for (ModuleTemplate moduleTemplate : moduleTemplates) {
            Module newModule = new Module();
            newModule.setName(moduleTemplate.getName())
                    .setDataAreas(moduleTemplate.getDataAreas())
                    .setDeviceId(form.getId());
            // 对象转换
            ObjectMapper objectMapper = new ObjectMapper();
            List<DataArea> dataAreasList = objectMapper.convertValue(moduleTemplate.getDataAreas(), new TypeReference<List<DataArea>>() {
            });

            // 构建Token
            StringBuilder stringBuilder = new StringBuilder("[");
            for (DataArea area : dataAreasList) {
                stringBuilder.append(area.getField()).append(",");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "").append("]");
            // 生成给Token，格式：clientId::[field1,field2,field3······]
            // token里面包含了模块的字段名,这样在数据入口处可以进行过滤。
            String token = ModuleTokenUtil.token(clientId + "::" + stringBuilder);
            newModule.setToken(token);
            newModule.setIcon(moduleTemplate.getIcon());
            // 如果是流媒体设备 需要在这里做一些操作
            // 1 可能在这里向阿里云之类的三方厂家申请Key
            // 2 然后把Key保存倒数据库，作为视频流设备的推流入口
            // 另一种就是自己搭建平台，一般人不会这么玩，所以这里暂时就默认为三方云服务
            if (moduleTemplate.getType().equals(ModuleTemplate.ModuleType.STREAM)) {
                StreamIntegration streamIntegration = new StreamIntegration();
                streamIntegration.setModuleId(newModule.getId());
                // 假设这里已经申请到了
                // 实际场景下要用三方SDK去生成
                // 开发阶段暂时不处理此处
                streamIntegration.setSecret("secret");
                streamIntegration.setToken("token");
                iStreamIntegrationService.save(streamIntegration);
            }
            iModuleService.save(newModule);
        }

        /**
         * 给MQTT协议的[设备]安装口令
         */
        if (product.getProtocol() == Device.MQTT) {

            // 数据上行
            MqttTopic s2cTopic = new MqttTopic();
            s2cTopic.setAccess(TOPIC_SUB)
                    .setType(MqttTopic.S2C)
                    .setClientId(clientId)
                    .setDeviceId(form.getId())
                    .setTopic("mqtt/out/" + form.getClientId() + "/s2c")
                    .setUsername(username);
            // 数据下行
            MqttTopic c2sTopic = new MqttTopic();
            c2sTopic.setAccess(TOPIC_PUB)
                    .setType(MqttTopic.C2S)
                    .setDeviceId(form.getId())
                    .setClientId(clientId)
                    .setTopic("mqtt/in/" + form.getClientId() + "/c2s")
                    .setUsername(username);
            // 状态上报
            MqttTopic statusTopic = new MqttTopic();
            statusTopic.setAccess(TOPIC_PUB)
                    .setType(MqttTopic.STATUS)
                    .setUsername(username)
                    .setClientId(clientId)
                    .setDeviceId(form.getId())
                    .setTopic("mqtt/in/" + form.getClientId() + "/status");
            // 生成
            iMqttTopicService.save(s2cTopic);
            iMqttTopicService.save(c2sTopic);
            iMqttTopicService.save(statusTopic);

        }
        return success();
    }

    /**
     * 查询模块数据定义
     *
     * @param deviceId
     * @return
     * @throws XException
     */
    @GetMapping("/queryDataStructureByDeviceId")
    public R queryDataStructureByDeviceId(@RequestParam Long deviceId) throws XException {
        Device device = iDeviceService.getById(deviceId);
        if (device == null) {
            throw new BizException("Device not exist", "设备不存在");
        }
        List<Module> moduleList = iModuleService.list(new QueryWrapper<Module>().eq("device_id", device.getId()));
        List<Map<String, Object>> moduleDataDefineList = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> featureCmdKeyDefineList = new ArrayList<>();

        for (Module module : moduleList) {
            Long moduleId = module.getId();
            List<DataArea> dataAreas = module.getDataAreas();
            Map<String, Object> moduleMap = new HashMap<>();
            moduleMap.put("moduleId", moduleId);
            moduleMap.put("deviceId", deviceId);
            moduleMap.put("structure", dataAreas);
            List<Feature> featureList = iFeatureService.list(new QueryWrapper<Feature>().eq("product_id", device.getProductId()));
            for (Feature feature : featureList) {
                Map<String, Object> featureMap = new HashMap<>();
                featureMap.put("featureId", feature.getId());
                featureMap.put("cmdKey", feature.getCmdKey());
                featureMap.put("productId", device.getProductId());
                featureCmdKeyDefineList.add(featureMap);
            }

            moduleDataDefineList.add(moduleMap);
        }
        data.put("modules", moduleDataDefineList);
        data.put("features", featureCmdKeyDefineList);
        data.put("parameter", device.getParameters());
        return data(data);
    }
}

