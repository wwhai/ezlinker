package com.ezlinker.app.config.socketio;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 客户端消息
 * @create 2019-11-27 22:31
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class C2SMessage extends XWSMsg {

    private String from;
    private Long moduleId;
    private Object data;

}
