package com.ezlinker.app.config.socketio;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

/**
 * @program: ezlinker
 * @description: SocketIOServer
 * @author: wangwenhai
 * @create: 2019-12-17 11:22
 **/
public class WSSocketIOServer extends SocketIOServer {
    public WSSocketIOServer(Configuration configuration) {
        super(configuration);
    }
}
