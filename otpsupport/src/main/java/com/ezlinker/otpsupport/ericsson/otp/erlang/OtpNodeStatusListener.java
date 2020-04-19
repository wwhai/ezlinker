package com.ezlinker.otpsupport.ericsson.otp.erlang;

public abstract class OtpNodeStatusListener {
    public abstract void remoteStatus(String node, boolean up, Object info);


    public abstract void localStatus(String node, boolean up, Object info);

    public abstract void connAttempt(String node, boolean incoming, Object info);
}