package com.ezlinker.app.modules.mqtttopic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;

import java.util.List;

/**
 * <p>
 * MQTT的TOPIC记录 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-18
 */
public interface IMqttTopicService extends IService<MqttTopic> {

    /**
     * 根据设备查Topic
     * @param deviceId
     * @return
     */
    List<MqttTopic> listByDevice(Long deviceId);

}
