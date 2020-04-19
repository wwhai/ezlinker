package com.ezlinker.javasdk.protocol;

import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * HTTP协议支持
 */
public final class HTTPProtocolSupport extends ProtocolSupport {
    @Override
    public void start() {

    }

    @Override
    public DataArea synchronizeState(ModuleState state) throws MqttException {
        return null;
    }


}
