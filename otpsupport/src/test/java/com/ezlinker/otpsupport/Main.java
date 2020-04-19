package com.ezlinker.otpsupport;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        JavaOtpNode javaNodeConnector = new JavaOtpNode("ezlinker@127.0.0.1", "ezlinkerself@127.0.0.1", "emqx@127.0.0.1", "emqxsecretcookie");
        javaNodeConnector.addListener(new OtpConnectListener() {

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onConnect() {
                System.out.println("节点连接成功");
            }

            @Override
            public void onData(String topic, String payload) {
                System.out.println("消息Topic:" + topic + " payload:" + payload);
            }
        });
        javaNodeConnector.start();
        new OtpNodeSupervisor(javaNodeConnector).start();

    }
}
