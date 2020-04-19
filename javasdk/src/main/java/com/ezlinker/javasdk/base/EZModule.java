package com.ezlinker.javasdk.base;

import com.ezlinker.javasdk.state.DataArea;

/**
 * 所有控件基类
 */
public abstract class EZModule {

    public EZModule() {
    }

    /**
     * 获取组件的名称
     *
     * @return
     */
    public abstract String getName();

    /**
     * 获取组件的一些属性
     *
     * @return
     */
    public abstract String getInfo();

    /**
     * 获取数据类型
     *
     * @return
     */
    public abstract Class<? extends DataArea> getDataArea();
}
