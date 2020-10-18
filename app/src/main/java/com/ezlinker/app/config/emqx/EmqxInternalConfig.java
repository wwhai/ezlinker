package com.ezlinker.app.config.emqx;

import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.config.gateway.MqttBizMessageHandler;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.devicelog.SystemLogService;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import com.ezlinker.app.modules.systemconfig.service.IEmqxConfigService;
import com.ezlinker.app.modules.systemlog.model.SystemLog;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wangwenhai
 * @date 2020/9/20
 * File description: emqx config
 */
@Configuration
public class EmqxInternalConfig {
    private static final Logger logger = LoggerFactory.getLogger(EmqxInternalConfig.class);
    private static final String USERNAME = "EZLINKER";
    private static final String PASSWORD = "EZLINKER";

    @Resource
    IEmqxConfigService iEmqxConfigService;
    @Resource
    SystemLogService systemLogService;
    @Resource
    RedisUtil redisUtil;
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    MqttBizMessageHandler mqttBizMessageHandler;

    private void init() {
        // 清除缓存,恢复现场
        redisUtil.del("DEVICE_ONLINE_COUNT");
        Set<Object> keys1 = redisUtil.sGet("EMQX_NODE_NAME*");
        Set<String> keys2 = redisUtil.keys("EMQX_NODE_STATE:*");
        Set<String> keys3 = redisUtil.keys("DEVICE_ON_OFF_LINE_STATE:*");
        for (Object key : keys1) {
            redisUtil.del(key.toString());
        }
        for (String key : keys2) {
            redisUtil.del(key);
        }

        for (String key : keys3) {
            redisUtil.del(key);
        }
    }

    /**
     * 加载emqx
     */
    @Bean("IMqttAsyncClient-EZLINKER-CLIENT")
    public PahoMqttPool pahoMqttPool() throws Exception {
        init();
        //连接
        List<IMqttAsyncClient> iMqttAsyncClients = new ArrayList<>();
        logger.info("尝试连接EMQX...");
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        List<EmqxConfig> emqxConfigs = iEmqxConfigService.list();
        if (emqxConfigs.isEmpty()) {
            throw new Exception("EMQX节点至少一个");
        }
        //目前这里是写死的：EZ_CLIENT-{i}
        int c = 0;
        for (EmqxConfig emqxConfig : emqxConfigs) {
            cacheEmqxNodeState(emqxConfig);
            c = c + 1;
            IMqttAsyncClient iMqttAsyncClient = new MqttAsyncClient("tcp://" + emqxConfig.getIp() + ":" + emqxConfig.getMqttPort(),
                    "EZLINKER-C" + c,
                    memoryPersistence);
            iMqttAsyncClients.add(iMqttAsyncClient);
            // Callback作用在连接成功后的时间内
            iMqttAsyncClient.setCallback(new MqttCallbackExtended() {

                /**
                 * 断线重连成功的接口
                 * TODO 本应该有个尝试连接的方法的 看了下Paho-mqtt的源码，这里没有回调
                 * TODO 已经在2020年9月24日提issue给Paho团队，如果没有解决准备自己实现一个.
                 * @param b
                 * @param s
                 */
                @Override
                public void connectComplete(boolean b, String s) {
                    init();
                    log("Emqx:[" + s + "]连接成功", "Emqx:[" + s + "连接成功]", SystemLog.SystemLogType.NORMAL);
                    logger.info("Emqx:[" + s + "]连接成功");
                }

                @Override
                public void connectionLost(Throwable throwable) {
                    throwable.printStackTrace();
                    log("Emqx:[" + iMqttAsyncClient.getServerURI() + "]连接失败",
                            "Emqx:[" + iMqttAsyncClient.getServerURI() + "]连接失败,尝试重连", SystemLog.SystemLogType.NORMAL);

                    try {
                        logger.error("尝试重连 emqx:[" + iMqttAsyncClient.getServerURI() + "]");
                        Thread.sleep(3000);
                        iMqttAsyncClient.reconnect();
                    } catch (MqttException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) {

                    System.out.println("Topic:" + topic + "=> Message: " + mqttMessage.toString());
                    if (topic.startsWith("$EZLINKER")) {
                        if (topic.endsWith("/c2s")) {
                            //TODO 后续实现
                            mqttBizMessageHandler.handleC2SMsg(topic, mqttMessage, redisUtil, mongoTemplate);
                        }
                        if (topic.endsWith("/status")) {
                            mqttBizMessageHandler.handleStatus(topic, mqttMessage, redisUtil, mongoTemplate);
                        }
                        if (topic.endsWith("/s2s")) {
                            //TODO 后续实现
                            mqttBizMessageHandler.handleS2CMsg(topic, mqttMessage, redisUtil, mongoTemplate);
                        }
                    }

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            // Listener是连接的时候发生的
            iMqttAsyncClient.connect(

                    mqttConnectOptions(), "EZLINKER", new

                            IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken iMqttToken) {
                                    logger.info("Success connect to emqx:" + iMqttAsyncClient.getServerURI());
                                    log("EZLINKER尝试连接EMQX" + iMqttAsyncClient.getServerURI(), "EZLINKER代理连接EMQX:[" + iMqttAsyncClient.getServerURI() + "]成功",
                                            SystemLog.SystemLogType.NORMAL);
                                    try {
                                        iMqttAsyncClient.subscribe("$EZLINKER/#", 2);
                                        logger.info("EZLINKER代理订阅EMQX:[$EZLINKER/#]成功");
                                        log("EZLINKER代理订阅EMQX:[$EZLINKER/#]成功", "EZLINKER代理订阅EMQX:[$EZLINKER/#]成功",
                                                SystemLog.SystemLogType.ERROR);

                                    } catch (MqttException e) {
                                        log("EZLINKER代理订阅EMQX:[$EZLINKER/#]失败", e.getMessage(), SystemLog.SystemLogType.ERROR);
                                        logger.error("EZLINKER代理订阅EMQX:[$EZLINKER/#]失败");
                                    }
                                }

                                @Override
                                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                                    logger.error("连接 emqx:[" + iMqttAsyncClient.getServerURI() + "]失败,错误信息:" + throwable.getMessage());
                                    try {
                                        logger.error("尝试重连 emqx:[" + iMqttAsyncClient.getServerURI() + "]");
                                        Thread.sleep(3000);
                                        iMqttAsyncClient.reconnect();
                                    } catch (MqttException | InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

        }
        return new PahoMqttPool(iMqttAsyncClients);
    }

    /**
     * 输出一些日志
     *
     * @param operation
     * @param message
     * @param type
     */
    private void log(String operation, String message, SystemLog.SystemLogType type) {
        SystemLog systemLog = new SystemLog();
        systemLog.setType(type);
        systemLog.setWho(EmqxInternalConfig.class.getCanonicalName());
        systemLog.setOperation(operation);
        systemLog.setMessage(message);
        systemLogService.save(systemLog);
    }

    /**
     * 同步状态到redis
     *
     * @param emqxConfig
     */
    private void cacheEmqxNodeState(EmqxConfig emqxConfig) {

        redisUtil.sSet(RedisKeyPrefix.EMQX_NODE_NAME, emqxConfig.getNodeName());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "ip", emqxConfig.getIp());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "nodeName", emqxConfig.getNodeName());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "mqttPort", emqxConfig.getMqttPort().toString());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "apiPort", emqxConfig.getApiPort().toString());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "appId", emqxConfig.getAppId());
        redisUtil.hset(RedisKeyPrefix.EMQX_NODE_STATE + emqxConfig.getNodeName(), "secret", emqxConfig.getSecret());

    }

    /**
     * 生成PahoMqtt的配置
     *
     * @return
     */
    private MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setUserName(USERNAME);
        mqttConnectOptions.setPassword(PASSWORD.toCharArray());
        mqttConnectOptions.setConnectionTimeout(1000);
        mqttConnectOptions.setMaxReconnectDelay(3000);
        mqttConnectOptions.setAutomaticReconnect(true);
        return mqttConnectOptions;
    }
}
