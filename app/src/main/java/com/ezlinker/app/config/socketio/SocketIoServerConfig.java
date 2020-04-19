//package com.ezlinker.app.config.socketio;
//
//import com.corundumstudio.socketio.SocketConfig;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.ezlinker.app.config.internalevent.SystemRealTimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//@Configuration
//public class SocketIoServerConfig implements ApplicationListener<SystemRealTimeMessage> {
//    private static ConcurrentHashMap<String, SocketIOClient> socketIoClientStore = new ConcurrentHashMap<>();
//
//    @Value("${socketio.host}")
//    private String host;
//    @Value("${socketio.port}")
//    private Integer port;
//    @Value("${socketio.bossCount}")
//    private int bossCount;
//    @Value("${socketio.workCount}")
//    private int workCount;
//    @Value("${socketio.allowCustomRequests}")
//    private boolean allowCustomRequests;
//    @Value("${socketio.upgradeTimeout}")
//    private int upgradeTimeout;
//    @Value("${socketio.pingTimeout}")
//    private int pingTimeout;
//    @Value("${socketio.pingInterval}")
//    private int pingInterval;
//
//    /**
//     * 创建SocketIO 服务器
//     *
//     * @return
//     */
//    public com.corundumstudio.socketio.Configuration getConfig() {
//        SocketConfig socketConfig = new SocketConfig();
//        socketConfig.setTcpNoDelay(true);
//        socketConfig.setSoLinger(0);
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setSocketConfig(socketConfig);
//        config.setHostname(host);
//        config.setPort(port);
//        config.setBossThreads(bossCount);
//        config.setWorkerThreads(workCount);
//        config.setAllowCustomRequests(allowCustomRequests);
//        config.setUpgradeTimeout(upgradeTimeout);
//        config.setPingTimeout(pingTimeout);
//        config.setPingInterval(pingInterval);
//        return config;
//    }
//
//    @Bean
//    public SocketIOServer socketIOServer() {
//        SocketIOServer server = new SocketIOServer(getConfig());
//        /**
//         * 连接
//         */
//        server.addConnectListener(client -> {
//            socketIoClientStore.put(client.getSessionId().toString(), client);
//        });
//        /**
//         * 离线
//         */
//        server.addDisconnectListener(client -> {
//            if (socketIoClientStore.contains(client)) {
//                socketIoClientStore.remove(client.getSessionId().toString());
//            }
//        });
//        server.startAsync();
//        return server;
//    }
//
//    /**
//     * 系统内部的事件
//     *
//     * @param systemRealTimeMessage
//     */
//    @Override
//    public void onApplicationEvent(SystemRealTimeMessage systemRealTimeMessage) {
//
//    }
//}