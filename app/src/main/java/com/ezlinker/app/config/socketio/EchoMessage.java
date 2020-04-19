package com.ezlinker.app.config.socketio;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 回复消息
 * @create 2019-12-16 22:30
 **/
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class EchoMessage extends XWSMsg {
    private String msg;
}
