package com.ezlinker.app.config.websocket;

import lombok.Data;

/**
 * @author wangwenhai
 * @date 2020/9/14
 * File description:
 */

@Data
class StompCommand {
    private String cmd;
    private String clientId;
    private String[] fields;
}
