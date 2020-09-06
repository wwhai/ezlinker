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
import com.ezlinker.app.utils.IDKeyUtil;
import com.ezlinker.app.utils.TokenUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
        return data(device);
    }

    /**
     * 条件检索
     *
     * @param current
     * @param size
     * @param name
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String sn,
            @RequestParam(required = false) String model) {

        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(projectId != null, "project_id", projectId);
        queryWrapper.eq(productId != null, "product_id", productId).or();
        queryWrapper.like((sn != null) && sn.length() > 0, "sn", sn).or();
        queryWrapper.like((model != null) && model.length() > 0, "model", model).or();
        queryWrapper.like((name != null) && name.length() > 0, "name", name).or();
        queryWrapper.like((industry != null) && industry.length() > 0, "industry", industry);
        queryWrapper.orderByDesc("create_time");
        // TODO 重新设计Page接口，使其可以返回项目和产品的名称
        // IPage<Device> devicePage = iDeviceService.queryForPage(sn, name, model, industry, new Page<>(current, size));
        IPage<Device> devicePage = iDeviceService.page(new Page<>(current, size), queryWrapper);
        return data(devicePage);
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
                .setUsername(username)
                .setPassword(password)
                .setClientId(clientId)
                .setProductId(product.getId())
                .setProjectId(product.getProjectId())
                .setLogo(product.getLogo())
                .setToken(TokenUtil.token(clientId))
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
            String token = TokenUtil.token(clientId + "::" + stringBuilder);
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
        return data(form);
    }
}

