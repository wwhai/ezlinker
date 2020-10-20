package com.ezlinker.app.modules.constant;

/**
 * @author wangwenhai
 * @date 2020/9/27
 * File description: Redis Key 统一放置
 */
public class RedisKeyPrefix {
    /**
     * EMQX节点名称
     */
    public static String EMQX_NODE = "EMQX_NODE";
    /**
     * 用户Token
     */
    public static String EZLINKER_USER_TOKEN = "EZLINKER_USER_TOKEN:";
    /**
     * 设备的参数
     */
    public static String DEVICE_FIELD_PARAMS = "DEVICE_FIELD_PARAMS:";
    /**
     * 节点状态
     */
    public static String EMQX_NODE_STATE = "EMQX_NODE_STATE:";
    /**
     * 设备当前运行状态
     */
    public static String DEVICE_RUNNING_STATE = "DEVICE_RUNNING_STATE:";
    /**
     * 设备在线数目
     */
    public static String DEVICE_ONLINE_COUNT = "DEVICE_ONLINE_COUNT:";
    /**
     * 设备在线状态
     */
    public static String DEVICE_ON_OFF_LINE_STATE = "DEVICE_ON_OFF_LINE_STATE:";

}
