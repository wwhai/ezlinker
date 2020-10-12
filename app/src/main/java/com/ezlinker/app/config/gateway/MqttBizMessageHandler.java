package com.ezlinker.app.config.gateway;

import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.model.MongoObject;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangwenhai
 * @date 2020/9/26
 * File description:
 */
@Service
public class MqttBizMessageHandler implements BizMessageHandler {
    @Resource
    IDeviceService iDeviceService;

    @Override
    public void handleS2CMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {

    }

    @Override
    public void handleC2SMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {

    }

    @Override
    public void handleStatus(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {
        String[] array = topic.split("/");
        if (array.length == 3) {
            String clientId = array[1];
            String payload = mqttMessage.toString();
            // 规定：自定义协议第一个字符必须是大写C，表示Custom
            // 然后第二个是分隔符,当前版本只支持单个char类型的字符
            // 一个Demo:C,[1,2,3,4,5,6,7...] 表示是自定义协议，用逗号分割

            if (payload.startsWith("C")) {
                String[] data = new TokenDelimiterConverter(payload).handler();
                if (data.length > 0) {
                    Object[] params = redisUtil.hmget(RedisKeyPrefix.DEVICE_FIELD_PARAMS + clientId).values().toArray();
                    int size = Math.min(params.length, data.length);
                    // 记录
                    Map<String, Object> record = new HashMap<>();
                    for (int i = 0; i < size; i++) {
                        record.put((String) params[i], data[i]);
                        // 更新状态
                        redisUtil.hset(RedisKeyPrefix.DEVICE_RUNNING_STATE + clientId, params[i].toString(), data[i]);
                    }
                    // 保存历史记录
                    MongoObject entity = new MongoObject();
                    entity.with("clientId", clientId);
                    entity.with("record", record);
                    iDeviceService.saveHistoryState(entity, clientId);
                    System.err.println("自定义协议:ClientId " + clientId + " " + Arrays.toString(data));
                    System.err.println("协议规范:ClientId " + clientId + " " + redisUtil.hmget(RedisKeyPrefix.DEVICE_FIELD_PARAMS + clientId));
                    System.err.println("保存的最终数据:ClientId " + clientId + " " + record);
                }
            } else {
                // JSON
                try {
                    JSONObject data = JSONObject.parseObject(payload);
                    // 历史记录
                    Map<String, Object> record = new HashMap<>();
                    for (String key : data.keySet()) {
                        // hset(RedisKeyPrefix.DEVICE_FIELD_PARAMS + clientId, area.getField(), area.getField());
                        boolean ok = redisUtil.hHasKey(RedisKeyPrefix.DEVICE_FIELD_PARAMS + clientId, key);
                        if (ok) {
                            // 更新状态
                            // hget(RedisKeyPrefix.DEVICE_RUNNING_STATE + deviceId, field);
                            redisUtil.hset(RedisKeyPrefix.DEVICE_RUNNING_STATE + clientId, key, data.get(key));
                            record.put(key, data.get(key));
                        }
                    }
                    MongoObject entity = new MongoObject();
                    entity.with("clientId", clientId);
                    entity.with("record", record);
                    iDeviceService.saveHistoryState(entity, clientId);
                    //保存历史记录
                } catch (Exception _e) {
                    // e.printStackTrace();
                }
            }
        }
    }
}
