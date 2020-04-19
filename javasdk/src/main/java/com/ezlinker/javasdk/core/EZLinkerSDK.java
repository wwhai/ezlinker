package com.ezlinker.javasdk.core;

import com.ezlinker.javasdk.base.EZComponent;
import com.ezlinker.javasdk.base.EZModule;
import com.ezlinker.javasdk.base.EZView;
import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.manager.ComponentManager;
import com.ezlinker.javasdk.manager.ViewManager;
import com.ezlinker.javasdk.state.DataArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 *
 */
public class EZLinkerSDK {
    /**
     * 控件管理器
     */
    public static final ComponentManager<EZComponent<DataArea>> componentManager = new ComponentManager<>();
    /**
     * 视图管理器
     */
    public static final ViewManager<EZView<DataArea>> viewManager = new ViewManager<>();
    private static Logger logger = LoggerFactory.getLogger(EZLinkerSDK.class);
    private String token;
    private ProtocolSupport protocolSupport;

    public static ComponentManager<EZComponent<DataArea>> getComponentManager() {
        return componentManager;
    }

    public static ViewManager<EZView<DataArea>> getViewManager() {
        return viewManager;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ProtocolSupport getProtocolSupport() {
        return protocolSupport;
    }

    public void setProtocolSupport(ProtocolSupport protocolSupport) {
        this.protocolSupport = protocolSupport;
    }

    public void init(String token, ProtocolSupport protocolSupport) {
        this.token = token;
        this.protocolSupport = protocolSupport;
    }

    /**
     * SDK开始运行
     */
    public void start() {
        protocolSupport.start();

    }

    /**
     * 注册模块
     * 1 注册组件的时候用三元构造函数：token listener support
     * 2 注册视图的时候用二元构造函数：token support
     *
     * @param token
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public EZModule register(String token, Class<? extends EZModule> clazz) throws Exception {

        if (clazz.getSuperclass() == (EZComponent.class)) {
            logger.debug("新控件注册成功:" + token);
            Constructor<? extends EZModule> constructor = clazz.getConstructor(String.class, ProtocolSupport.class);

            EZComponent<DataArea> ezComponent = (EZComponent<DataArea>) constructor.newInstance(token, protocolSupport);
            componentManager.put(token, ezComponent);
            return ezComponent;

        }
        if (clazz.getSuperclass() == (EZView.class)) {
            logger.debug("新视图注册成功:" + token);
            Constructor<? extends EZModule> constructor = clazz.getConstructor(String.class, ProtocolSupport.class);

            EZView<DataArea> ezView = (EZView<DataArea>) constructor.newInstance(token, protocolSupport);
            viewManager.put(token, ezView);
            return ezView;

        }
        return null;

    }


}
