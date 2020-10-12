package com.ezlinker.app.config.gateway;

import com.ezlinker.app.common.utils.RedisUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wangwenhai
 * @date 2020/10/8
 * File description: COAP协议支持
 */
@Service
public class CoapBizMessageHandler implements BizMessageHandler {
    @Override
    public void handleS2CMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {

    }

    @Override
    public void handleC2SMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {

    }

    @Override
    public void handleStatus(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate) {

    }
}
