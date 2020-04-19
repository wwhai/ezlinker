package com.ezlinker.app.config.mqtt;

import org.eclipse.paho.client.mqttv3.*;

/**
 * @program: ezlinker
 * @description: 代理客户端
 * @author: wangwenhai
 * @create: 2019-12-17 10:01
 **/
public class MqttProxyClient extends MqttClient {

    public MqttProxyClient(String serverUrl, String clientId) throws MqttException {
        super(serverUrl, clientId);
    }


    @Override
    public void connect(MqttConnectOptions options) throws MqttException {
        super.connect(options);
    }

    @Override
    public void disconnect() throws MqttException {
        super.disconnect();
    }

    @Override
    public void subscribe(String topicFilter) throws MqttException {
        super.subscribe(topicFilter);
    }


    @Override
    public void publish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
        super.publish(topic, payload, qos, retained);
    }

    @Override
    public void close() throws MqttException {
        super.close();
    }



}
