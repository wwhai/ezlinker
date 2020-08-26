package com.ezlinker.app.emqintegeration.message;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @program: ezlinker
 * @description: 离线消息
 * @author: wangwenhai
 * @create: 2019-11-21 11:11
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class DisconnectedMessage implements Serializable {

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
     * 离线原因
     */
    private String reason;

}
