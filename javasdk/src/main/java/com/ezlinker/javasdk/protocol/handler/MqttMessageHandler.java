package com.ezlinker.javasdk.protocol.handler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public abstract class MqttMessageHandler implements MqttCallback {

    /**
     * 链接断开
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {

        onError(throwable);
    }

    /**
     * 收到消息
     * {
     * "token":"模块的Token",
     * "dataArea":{
     * "k1":"k1",
     * "k2":"k2"
     * }
     * }
     *
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String payload = mqttMessage.toString();
        onData(payload);
    }

    /**
     * 消息发送成功
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    /**
     * 出错处理
     */
    public abstract void onError(Throwable throwable);

    /**
     *
     */
    public abstract void onConnected() throws MqttException;

    /**
     * @param data
     */
    public abstract void onData(Object data) throws MqttException;
}
