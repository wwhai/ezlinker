package com.ezlinker.otpsupport;

import com.ezlinker.otpsupport.ericsson.otp.erlang.OtpNode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 节点连接监控器
 */
public class OtpNodeSupervisor extends Thread {
    ExecutorService pool = Executors.newSingleThreadExecutor(Thread::new);
    private JavaOtpNode javaOtpNode;

    public OtpNodeSupervisor(JavaOtpNode javaOtpConnector) {
        this.javaOtpNode = javaOtpConnector;
        setName("OtpNodeSupervisorThread");
    }


    @Override
    public void run() {
        // 如果线程存活 就使劲ping
        while (true) {
            try {
                Thread.sleep(500);
                OtpNode otpNode = this.javaOtpNode.getOtpNode();
                if (otpNode != null) {
                    boolean result = otpNode.ping(this.javaOtpNode.getPeerName(), 500);
                    if (result == false) {
                        pool.execute(this.javaOtpNode);
                        //System.out.println("节点已经连接失败:" + this.javaOtpNode.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
