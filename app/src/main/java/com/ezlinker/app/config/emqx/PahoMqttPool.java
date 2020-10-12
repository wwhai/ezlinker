package com.ezlinker.app.config.emqx;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;

import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/9/20
 * File description: A simple mqtt client pool
 */
public class PahoMqttPool {
    private List<IMqttAsyncClient> iMqttAsyncClients;

    public PahoMqttPool(List<IMqttAsyncClient> iMqttAsyncClients) {
        this.iMqttAsyncClients = iMqttAsyncClients;
    }

    public IMqttAsyncClient get() {
        return iMqttAsyncClients.get((int) (Math.random() * iMqttAsyncClients.size()));
    }

    public List<IMqttAsyncClient> getiMqttAsyncClients() {
        return iMqttAsyncClients;
    }

    public void setiMqttAsyncClients(List<IMqttAsyncClient> iMqttAsyncClients) {
        this.iMqttAsyncClients = iMqttAsyncClients;
    }
}
