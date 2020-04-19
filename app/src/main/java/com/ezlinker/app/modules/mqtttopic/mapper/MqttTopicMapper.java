package com.ezlinker.app.modules.mqtttopic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;

import java.util.List;

/**
 * <p>
 * MQTT的TOPIC记录 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-18
 */
public interface MqttTopicMapper extends BaseMapper<MqttTopic> {

    /**
     * 根据设备查Topic
     * @param deviceId
     * @return
     */
    List<MqttTopic> listByDevice(Long deviceId);
}
