package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * EMQX配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_emqx_config")
public class EmqxConfig extends XEntity {
    /**
     * EMQX IP地址
     */
    @NotEmpty(message = "IP地址不可为空值")
    private String ip;

    /**
     * HTTP接口的ID
     */
    @NotEmpty(message = "appId不可为空值")

    private String appId;

    /**
     * APP接口密钥
     */
    @NotEmpty(message = "secret不可为空值")
    private String secret;
    /**
     * 集群节点名称
     */
    @NotEmpty(message = "节点名称不可为空值,格式为:nodeName@ip")
    private String nodeName;
    /**
     *  HTTP API 端口
     */
    private Integer apiPort = 8081;
    /**
     *  MQTT TCP端口
     */
    private Integer mqttPort = 1883;
    /**
     * 描述
     */
    private String description;
}
