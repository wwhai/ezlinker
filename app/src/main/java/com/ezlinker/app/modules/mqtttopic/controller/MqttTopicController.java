package com.ezlinker.app.modules.mqtttopic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.mqtttopic.model.MqttTopic;
import com.ezlinker.app.modules.mqtttopic.service.IMqttTopicService;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * MQTT的TOPIC记录
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/mqtttopics")
public class MqttTopicController extends CurdController<MqttTopic> {

    @Autowired
    IMqttTopicService iMqttTopicService;

    public MqttTopicController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R get(Long id) throws XException {
        return super.get(id);
    }

    /**
     * 获取某个模块的所有Topic列表
     *
     * @param clientId
     * @return
     */
    @GetMapping
    public R queryForList(@RequestParam String clientId) {

        List<MqttTopic> list = iMqttTopicService.list(new QueryWrapper<MqttTopic>()
                .eq("client_id", clientId)
                .orderByDesc("create_time"));
        return data(list);
    }
    /**
     * 类型 1：S2C；2：C2S；3：STATUS；4：GROUP
     * @return
     */
    @GetMapping("/types")
    public R getType() {
        HashMap<String, Object> data1 = new HashMap<>();
        data1.put("label", "服务端指令通道");
        data1.put("value", 1);

        HashMap<String, Object> data2 = new HashMap<>();
        data2.put("label", "设备上传通道");
        data2.put("value", 2);

        HashMap<String, Object> data3 = new HashMap<>();
        data3.put("label", "设备状态通道");
        data3.put("value", 3);

        HashMap<String, Object> data4 = new HashMap<>();
        data4.put("label", "分组广播消息");
        data4.put("value", 4);

        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);

        return data(list);
    }


}

