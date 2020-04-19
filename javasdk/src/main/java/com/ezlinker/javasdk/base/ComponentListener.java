package com.ezlinker.javasdk.base;

import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class ComponentListener<T> {

    public abstract T onData(T t) throws MqttException;

    public abstract void onError(String error);


}
