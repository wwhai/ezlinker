package com.ezlinker.app.modules.dataentry.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wangwenhai
 * @date 2020/9/24
 * File description:
 */
@Data
public class AdvisoryMessage {
    @NotEmpty(message = "Field 'action' can't empty")
    private String action;
    @NotEmpty(message = "Field 'username' can't empty")
    private String username;
    @NotEmpty(message = "Field 'clientid' can't empty")
    private String clientid;
    private String reason;
    private String ipaddress;

    @Override
    public String toString() {
        return "AdvisoryMessage{" +
                "action='" + action + '\'' +
                ", clientid='" + clientid + '\'' +
                ", reason='" + reason + '\'' +
                ", username='" + username + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                '}';
    }
}
