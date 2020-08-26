package com.ezlinker.app.config.internalevent;

import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * @program: ezlinker
 * @description: 系统内部实时消息，主要用在给前端推送
 * @author: wangwenhai
 * @create: 2020-01-08 10:33
 **/
@EqualsAndHashCode(callSuper = true)
public class SystemRealTimeMessage extends ApplicationEvent {


    private String destination;
    private String type;
    private String content;

    public SystemRealTimeMessage(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "SystemRealTimeMessage{" +
                "destination='" + destination + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
