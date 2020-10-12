package com.ezlinker.app.config.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/9/11
 * File description: Websocket support
 */
@RestController
public class WebSocketController {
    @Resource
    IDeviceService iDeviceService;

    /**
     * 收到来自客户端的Topic是MessageMapping的消息,
     * 然后把结果返回给订阅了SendTo Topic的客户端
     *
     * @param message
     * @return
     */

    @MessageMapping("/ws2system")
    @SendTo("/s2system")
    public Object system(@Payload StompCommand message) {
        System.out.println("[===>]  Payload: " + (message));

        return "ok";
    }

    @MessageMapping("/ws2device")
    @SendTo("/s2device")
    public String device(@Payload StompCommand message) {
        if (message.getCmd().equals("DEVICE_STATE")) {
            List<LinkedHashMap<String, Object>> list = iDeviceService.getLastState(message.getClientId(), message.getFields());
            return JSON.toJSONString(list, SerializerFeature.WriteMapNullValue);
        }
        return "null";
    }


    @MessageMapping("/ws2module")
    @SendTo("/s2module")
    public String module(@Payload StompCommand message) {
        System.out.println("I am from client:" + message);
        return "hello i am server";
    }
}
