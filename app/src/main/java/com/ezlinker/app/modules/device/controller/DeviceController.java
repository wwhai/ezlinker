package com.ezlinker.app.modules.device.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.dataentry.service.DeviceDataService;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.ezlinker.app.modules.devicelog.DeviceLogService;
import com.ezlinker.app.modules.emqx.monitor.EMQMonitorV4;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;
import com.ezlinker.app.modules.mqtttopic.service.IMqttTopicService;
import com.ezlinker.app.modules.product.model.Product;
import com.ezlinker.app.modules.product.service.IProductService;
import com.ezlinker.app.utils.IDKeyUtil;
import com.ezlinker.app.utils.TokenUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    RedisUtil redisUtil;
    @Resource
    IProductService iProductService;
    @Resource
    IDeviceService iDeviceService;
    @Resource
    DeviceDataService deviceDataService;

    @Resource
    DeviceLogService deviceLogService;
    @Resource
    IMqttTopicService iMqttTopicService;

    @Resource
    IDKeyUtil idKeyUtil;
    @Resource
    MongoTemplate mongoTemplate;

    public DeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
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
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") Long projectId,
            @RequestParam(required = false, defaultValue = "") Long productId,
            @RequestParam(required = false, defaultValue = "") String industry,
            @RequestParam(required = false, defaultValue = "") String sn,
            @RequestParam(required = false, defaultValue = "") String model) {
        IPage<Device> devicePage = iDeviceService.queryForPage(sn, name, projectId, productId, model, industry, new Page<>(current, size));
        return data(devicePage);
    }

    /**
     * 删除设备
     *
     * @param ids
     * @return
     * @throws XException
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{ids}")
    @Override
    protected R delete(@PathVariable Integer[] ids) throws XException {
        for (Device device : iDeviceService.listByIds(Arrays.asList(ids))) {
            Map<Object, Object> runningMap = redisUtil.hmget(RedisKeyPrefix.DEVICE_RUNNING_STATE + device.getClientId());
            if (runningMap != null && (!runningMap.isEmpty())) {
                throw new BizException("", "设备在运行状态,不可删除,建议先停止设备");
            } else {
                // 删除所有Topic
                iMqttTopicService.remove(new QueryWrapper<MqttTopic>().eq("client_id", device.getClientId()));
                // 删除设备
                iDeviceService.remove(new QueryWrapper<Device>().eq("id", device.getId()));
                // 删除Redis中的缓存
                if (redisUtil.hasKey(RedisKeyPrefix.DEVICE_RUNNING_STATE + device.getClientId())) {
                    redisUtil.del(RedisKeyPrefix.DEVICE_RUNNING_STATE + device.getClientId());

                }
                if (redisUtil.hasKey(RedisKeyPrefix.DEVICE_FIELD_PARAMS + device.getClientId())) {
                    redisUtil.del(RedisKeyPrefix.DEVICE_FIELD_PARAMS + device.getClientId());
                }
                // 删除历史数据
                mongoTemplate.dropCollection(MongoCollectionPrefix.DEVICE_HISTORY_DATA + device.getClientId());
            }
        }
        return success();
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
        form.setFieldParams(product.getFieldParams())
                .setUsername(username)
                .setPassword(password)
                .setClientId(clientId)
                .setProductId(product.getId())
                .setProjectId(product.getProjectId())
                .setLogo(product.getLogo())
                .setToken(TokenUtil.token(clientId))
                .setSn("SN" + idKeyUtil.nextId());
        for (FieldParam area : new ObjectMapper().convertValue(form.getFieldParams(), new TypeReference<List<FieldParam>>() {
        })) {
            redisUtil.hset(RedisKeyPrefix.DEVICE_FIELD_PARAMS + clientId, area.getField(), area.getField());
        }

        iDeviceService.save(form);

        /**
         * 给MQTT协议的[设备]安装口令
         */

        // 服务器->>> 客户端
        MqttTopic s2cTopic = new MqttTopic();
        s2cTopic.setAccess(TOPIC_SUB)
                .setType(MqttTopic.S2C)
                .setClientId(clientId)
                .setDeviceId(form.getId())
                .setTopic("$EZLINKER/" + form.getClientId() + "/s2c")
                .setUsername(username);
        // 客户端->>>服务器
        MqttTopic c2sTopic = new MqttTopic();
        c2sTopic.setAccess(TOPIC_PUB)
                .setType(MqttTopic.C2S)
                .setDeviceId(form.getId())
                .setClientId(clientId)
                .setTopic("$EZLINKER/" + form.getClientId() + "/c2s")
                .setUsername(username);
        // 客户端->>>服务器
        MqttTopic statusTopic = new MqttTopic();
        statusTopic.setAccess(TOPIC_PUB)
                .setType(MqttTopic.STATUS)
                .setUsername(username)
                .setClientId(clientId)
                .setDeviceId(form.getId())
                .setTopic("$EZLINKER/" + form.getClientId() + "/status");
        // 生成
        iMqttTopicService.save(s2cTopic);
        iMqttTopicService.save(c2sTopic);
        iMqttTopicService.save(statusTopic);
        return data(form);
    }

    /**
     * 获取状态,状态保存在Redis里面
     *
     * @param clientId
     * @param fields
     * @return
     */
    @GetMapping("/lastState")
    public R getLastState(@PathVariable String clientId, @PathVariable String[] fields) {
        List<LinkedHashMap<String, Object>> list = iDeviceService.getLastState(clientId, fields);
        return data(list);
    }

    /**
     * @param clientIds
     * @return
     */
    @PostMapping("/onlineState")
    public R getOnlineState(@RequestBody String[] clientIds) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (String clientId : clientIds) {
            Object state = redisUtil.get("DEVICE_ON_OFF_LINE_STATE:" + clientId);
            Map<String, Object> map = new HashMap<>();
            if (state != null) {
                map.put("clientId", clientId);
                map.put("state", state);
                list.add(map);
            } else {
                map.put("clientId", clientId);
                map.put("state", "OFFLINE");
                list.add(map);
            }

        }
        return data(list);
    }

    /**
     * 获取系统内设备的上下线记录
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/deviceLogs")
    public R deviceLogs(@RequestParam(required = false, defaultValue = "0") Integer current,
                        @RequestParam(required = false, defaultValue = "20") Integer size,
                        @RequestParam(required = false, defaultValue = "") String clientId) {
        Pageable pageable = getXPageRequest(current, size);
        return data(deviceLogService.queryForPage(clientId, pageable));

    }

    /**
     * 设备历史数据
     *
     * @param current
     * @param size
     * @param clientId
     * @return
     */
    @GetMapping("/deviceData")
    public R deviceData(@RequestParam(required = false, defaultValue = "0") Integer current,
                        @RequestParam(required = false, defaultValue = "20") Integer size,
                        @RequestParam String clientId) {
        Pageable pageable = getXPageRequest(current, size);
        return data(deviceDataService.queryForPage(clientId, pageable));

    }

    /**
     * 获取历史状态
     *
     * @param current
     * @param size
     * @param clientId
     * @param fields
     * @return
     */
    @GetMapping("/deviceState")
    public R deviceState(@RequestParam(required = false, defaultValue = "0") Integer current,
                         @RequestParam(required = false, defaultValue = "20") Integer size,
                         @RequestParam String clientId, @RequestParam String[] fields) {
        Pageable pageable = getXPageRequest(current, size);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("fields", fields);
        map.put("data", iDeviceService.getHistoryState(clientId, fields, pageable));
        return data(map);

    }


    /**
     * 强制重连
     *
     * @param clientId
     * @return
     */

    @RequestMapping("/forceOffline")
    public R forceOffline(@RequestParam String clientId) throws BizException, BadRequestException {

        Set<Object> nodes = redisUtil.sGet(RedisKeyPrefix.EMQX_NODE_NAME);
        if (nodes.isEmpty()) {
            throw new BizException("EMQX节点离线", "EMQX节点离线");

        }
        Map<Object, Object> node = redisUtil.hmget(RedisKeyPrefix.EMQX_NODE_STATE + nodes.toArray()[0].toString());

        if (nodes.isEmpty()) {
            throw new BizException("EMQX节点离线", "EMQX节点离线");
        }

        String ip = node.get("ip").toString();
        Integer apiPort = Integer.parseInt(node.get("apiPort").toString());
        String appId = node.get("appId").toString();
        String secret = node.get("secret").toString();
        Integer code = EMQMonitorV4.kickoff(ip, apiPort, clientId, appId, secret);
        if (code == 0) {
            return success();
        } else if (code == 112) {
            throw new BizException("客户端已经离线", "客户端已经离线");
        } else {
            throw new BadRequestException("操作失败", "操作失败");
        }
    }
}

