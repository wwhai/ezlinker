package com.ezlinker.app.common.web;

/**
 * 动态POST请求参数
 *
 * @param <T>
 */
public class XRequestArgs<T> {
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
