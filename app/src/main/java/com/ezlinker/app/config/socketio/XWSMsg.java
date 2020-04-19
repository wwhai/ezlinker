package com.ezlinker.app.config.socketio;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 消息类,主要用在WS和服务器的交互
 * @create 2019-12-16 22:21
 **/
public class XWSMsg {
    private String msgId;
    private Integer code;

    private boolean debug;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public boolean isDebug() {
        return debug;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
