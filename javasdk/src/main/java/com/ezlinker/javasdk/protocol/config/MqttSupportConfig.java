package com.ezlinker.javasdk.protocol.config;

/**
 * MQTT 协议支持的额外配置
 */
public class MqttSupportConfig {
    private String s2cTopic;
    private String c2sTopic;
    private String stateTopic;

    public MqttSupportConfig(String s2cTopic, String c2sTopic, String stateTopic) {
        this.s2cTopic = s2cTopic;
        this.c2sTopic = c2sTopic;
        this.stateTopic = stateTopic;
    }

    public String getS2cTopic() {
        return s2cTopic;
    }

    public void setS2cTopic(String s2cTopic) {
        this.s2cTopic = s2cTopic;
    }

    public String getC2sTopic() {
        return c2sTopic;
    }

    public void setC2sTopic(String c2sTopic) {
        this.c2sTopic = c2sTopic;
    }

    public String getStateTopic() {
        return stateTopic;
    }

    public void setStateTopic(String stateTopic) {
        this.stateTopic = stateTopic;
    }
}
