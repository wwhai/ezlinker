package com.ezlinker.app.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author wangwenhai
 * @date 2020/8/23
 * File description: Websocket config
 */
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // var socket = new SockJS('/ws');
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 类似于注册全局Topic，供给Javascript来subscribe
        // stompClient.subscribe('/system', function () {});
        // stompClient.subscribe('/device', function () {});
        // stompClient.subscribe('/module', function () {});
        registry.enableSimpleBroker("/s2system", "/s2device", "/s2module");
    }
}
