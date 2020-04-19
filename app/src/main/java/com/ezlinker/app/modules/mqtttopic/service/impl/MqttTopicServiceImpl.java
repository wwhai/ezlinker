package com.ezlinker.app.modules.mqtttopic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.mqtttopic.mapper.MqttTopicMapper;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;
import com.ezlinker.app.modules.mqtttopic.service.IMqttTopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * MQTT的TOPIC记录 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-18
 */
@Service
public class MqttTopicServiceImpl extends ServiceImpl<MqttTopicMapper, MqttTopic> implements IMqttTopicService {
    @Resource
    MqttTopicMapper mqttTopicMapper;

    @Override
    public List<MqttTopic> listByDevice(Long deviceId) {
        return mqttTopicMapper.listByDevice(deviceId);
    }

}
