package com.ezlinker.javasdk.base;

import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 协议支持类
 */
public abstract class ProtocolSupport {
    public abstract void start();

    /**
     * 最终把area反馈给服务端
     *
     * @param state
     * @return
     */
    public abstract DataArea synchronizeState(ModuleState state) throws MqttException;

}
