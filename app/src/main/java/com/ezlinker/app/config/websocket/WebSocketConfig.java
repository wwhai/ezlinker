package com.ezlinker.app.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangwenhai
 * @date 2020/8/23
 * File description: Websocket config
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    // Session<==>requestId
    private static final ConcurrentHashMap<WebSocketSession, String> SESSION_MAP = new ConcurrentHashMap<>();
    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new WebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
                SESSION_MAP.put(webSocketSession, webSocketSession.getId());
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage("ok"));
                }
            }

            @Override
            public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {

            }

            @Override
            public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
                SESSION_MAP.remove(webSocketSession);
            }

            @Override
            public boolean supportsPartialMessages() {
                return false;
            }
        }, "/ws");
    }
}
