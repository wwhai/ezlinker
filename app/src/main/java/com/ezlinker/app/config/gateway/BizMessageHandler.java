package com.ezlinker.app.config.gateway;

import com.ezlinker.app.common.utils.RedisUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author wangwenhai
 * @date 2020/9/26
 * File description:
 */
public interface BizMessageHandler {
    /**
     * 处理服务器给设备推送的数据
     *
     * @param topic
     * @param mqttMessage
     * @param redisUtil
     * @param mongoTemplate
     */
    void handleS2CMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate);

    /**
     * 处理客户端上行数据
     *
     * @param topic
     * @param mqttMessage
     * @param redisUtil
     * @param mongoTemplate
     */
    void handleC2SMsg(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate);

    /**
     * 处理状态同步数据
     *
     * @param topic
     * @param mqttMessage
     * @param redisUtil
     * @param mongoTemplate
     */
    void handleStatus(String topic, MqttMessage mqttMessage, RedisUtil redisUtil, MongoTemplate mongoTemplate);

}
