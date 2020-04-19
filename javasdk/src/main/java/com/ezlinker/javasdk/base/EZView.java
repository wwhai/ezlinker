package com.ezlinker.javasdk.base;

/**
 * 展示类组件
 */
public abstract class EZView<T> extends EZModule {
    private String token;
    private ProtocolSupport protocolSupport;

    public EZView() {
    }

    public EZView(String token) {
        this.token = token;
    }

    public EZView(String token, ProtocolSupport protocolSupport) {
        this.token = token;
        this.protocolSupport = protocolSupport;
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

    /**
     * 上传数据
     *
     * @param t
     */
    protected abstract void push(T t);

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
