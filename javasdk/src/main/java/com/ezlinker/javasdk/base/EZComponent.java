package com.ezlinker.javasdk.base;

import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可操作控件
 */
public abstract class EZComponent<T> extends EZModule {
    private static Logger logger = LoggerFactory.getLogger(EZComponent.class);

    private String token;
    private ComponentListener<T> componentListener;
    private ProtocolSupport protocolSupport;

    public EZComponent(String token, ProtocolSupport protocolSupport) {
        this.token = token;
        this.protocolSupport = protocolSupport;
    }

    /**
     * 同步状态:给服务端反馈自己最终的本地数据，此数据会被记录在数据库，作为最终的终端状态
     * 简明扼要就是：最后一次的本地状态代表模块的T最终状态
     */
    public DataArea synchronizeState(ModuleState moduleState) throws MqttException {
        logger.debug("同步状态:" + moduleState);
        return protocolSupport.synchronizeState(moduleState);
    }

    public void setComponentListener(ComponentListener<T> componentListener) {
        this.componentListener = componentListener;
    }

    public ProtocolSupport getProtocolSupport() {
        return protocolSupport;
    }

    public void setProtocolSupport(ProtocolSupport protocolSupport) {
        this.protocolSupport = protocolSupport;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ComponentListener<T> getComponentListener() {
        return componentListener;
    }


    public void addListener(ComponentListener<T> componentListener) {
        this.componentListener = componentListener;
    }


}
