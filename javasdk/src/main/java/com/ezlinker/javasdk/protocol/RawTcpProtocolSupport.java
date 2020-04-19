package com.ezlinker.javasdk.protocol;

import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 原生TCP协议
 */
public final class RawTcpProtocolSupport extends ProtocolSupport {
    @Override
    public void start() {

    }

    @Override
    public DataArea synchronizeState(ModuleState state) throws MqttException {
        return null;
    }


}
