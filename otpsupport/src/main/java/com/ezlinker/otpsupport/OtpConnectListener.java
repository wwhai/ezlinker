package com.ezlinker.otpsupport;

/**
 * Otp事件监听器
 */
public abstract class OtpConnectListener {

    public abstract void onError(Exception e);

    public abstract void onConnect();

    public abstract void onData(String topic, String payload);

}
