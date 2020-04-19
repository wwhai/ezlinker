package com.ezlinker.app.modules.mqtttopic.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * MQTT的TOPIC记录
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_mqtt_topic")
public class MqttTopic extends XEntity {


    /**
     * 类型 1：S2C；2：C2S；3：STATUS；4：GROUP
     */
    public static final int S2C = 1;
    public static final int C2S = 2;
    public static final int STATUS = 3;
    public static final Integer GROUP = 4;

    /**
     * 允许
     */
    private static final int ALLOW = 1;
    /**
     * 拒绝
     */
    private static final int DENY = 0;

    private static final long serialVersionUID = 1L;

    /**
     * 是否允许连接: 0=拒绝1=允许
     */
    private Integer allow = ALLOW;

    /**
     * IP
     */
    private String ip = "0.0.0.0";

    /**
     * MQTT用户名
     */
    private String username;

    /**
     * MQTT客户端ID
     */
    private String clientId;

    /**
     * 行为类型: 1=订阅2=发布3=订阅+发布
     */
    private Integer access;

    /**
     * 路由
     */
    private String topic;

    /**
     * 设备的ID
     */
    private Long deviceId;
    /**
     * 类型 1：S2C；2：C2S；3：STATUS；4：GROUP
     */
    private Integer type;

}
