package com.ezlinker.app.emqintegeration.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @program: ezlinker
 * @description: mqtt客户端连接成功
 * @author: wangwenhai
 * @create: 2019-11-21 11:10
 **/
@Data
@EqualsAndHashCode(callSuper = false)

public class ConnectedMessage implements Serializable {

    /**
     * 触发的动作
     */

    private String action;
    /**
     * 客户端ID
     */
    @NotEmpty(message = "clientId can't null")
    private String clientId;
    /**
     * MQTT Username
     */
    private String username;
    /**
     * IP地址
     */
    private String ip;


}
