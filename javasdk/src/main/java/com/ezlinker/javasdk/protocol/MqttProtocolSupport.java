package com.ezlinker.javasdk.protocol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ezlinker.javasdk.base.EZModule;
import com.ezlinker.javasdk.base.ProtocolSupport;
import com.ezlinker.javasdk.core.EZLinkerSDK;
import com.ezlinker.javasdk.protocol.config.MqttSupportConfig;
import com.ezlinker.javasdk.protocol.handler.MqttMessageHandler;
import com.ezlinker.javasdk.state.DataArea;
import com.ezlinker.javasdk.state.ModuleState;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * MQTT 协议支持
 */

public final class MqttProtocolSupport extends ProtocolSupport {

    private static Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);
    private MqttClient client;
    private String host;
    private int port;
    private String clientId;
    private String username;
    private String password;
    private MqttSupportConfig mqttSupportConfig;

    /**
     * @param host
     * @param port
     * @param clientId
     * @param password
     * @param password
     */
    public MqttProtocolSupport(String host, int port, String clientId, String userName, String password, MqttSupportConfig mqttSupportConfig) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        this.username = userName;
        this.password = password;
        this.mqttSupportConfig = mqttSupportConfig;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        MqttProtocolSupport.logger = logger;
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MqttSupportConfig getMqttSupportConfig() {
        return mqttSupportConfig;
    }

    public void setMqttSupportConfig(MqttSupportConfig mqttSupportConfig) {
        this.mqttSupportConfig = mqttSupportConfig;
    }

    /**
     * @param messageHandler
     */

    public void connect(MqttMessageHandler messageHandler) {
        try {
            client = new MqttClient("tcp://" + host + ":" + port, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            //保存会话
            options.setCleanSession(false);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            client.setCallback(messageHandler);
            client.connect(options);
            if (client.isConnected()) {
                messageHandler.onConnected();

            } else {
                messageHandler.onError(new Exception("连接失败!"));
            }
        } catch (Exception e) {
            messageHandler.onError(e);
        }
    }

    /**
     * 停止
     *
     * @throws MqttException
     */
    public void stop() throws MqttException {
        client.disconnect();
        client.close();
    }

    /**
     *
     */
    @Override
    public void start() {
        connect(new MqttMessageHandler() {
            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onConnected() throws MqttException {
                logger.info("服务器连接成功!");
                client.subscribe(getMqttSupportConfig().getS2cTopic());
                logger.info("加入设备Topic成功:" + getMqttSupportConfig().getS2cTopic());

            }

            @Override
            public void onData(Object rawData) throws MqttException {
                logger.debug("收到数据:" + rawData);

                try {
                    JSONObject fromJson = JSONObject.parseObject(rawData.toString());
                    String token = fromJson.getString("token");
                    if (token != null) {
                        JSONObject dataArea = fromJson.getJSONObject("dataArea");
                        if (dataArea != null) {
                            // 查表,这里明确就是查控件管理表
                            // 状态同步机制实现：synchronizeState 方法用来反馈状态，最终上传至服务器
                            if (EZLinkerSDK.getComponentManager().get(token) != null) {
                                // 给组件通知
                                // 需要提取消息类型
                                EZModule ezModule = EZLinkerSDK.getComponentManager().get(token);
                                EZLinkerSDK.getComponentManager().get(token).getComponentListener().onData(JSON.parseObject(dataArea.toString(), ezModule.getDataArea()));

                            } else {
                                onError(new Exception("组件不存在!"));

                            }

                        } else {
                            onError(new Exception("数据不可为空值!"));

                        }
                    } else {
                        onError(new Exception("数据包中Token缺失!"));

                    }

                } catch (JSONException e) {
                    onError(e);
                }

            }
        });
    }


    /**
     * 这里返回新状态
     *
     * @return
     */

    @Override
    public DataArea synchronizeState(ModuleState moduleState) throws MqttException {
        JSONObject data = new JSONObject();
        data.put("token", moduleState.getToken());
        data.put("data", JSONObject.toJSON(moduleState.getArea()));
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(data.toJSONString().getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(2);
        client.publish(getMqttSupportConfig().getC2sTopic(), mqttMessage);
        return moduleState.getArea();
    }


}
