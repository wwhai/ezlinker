# 魔改爱立信的ErlangJavaInterface库
## 1.背景
近期在用JInterface实现一些功能，发现爱立信提供的Java库已经很老了，最后一个版本是2004年写的，至今已经无人维护。
有很多蹩脚的语法和写法，现在看来非常奇怪。据说是开源的但是我找不到源码地址，有知道的朋友可以联系一下我。于是我用了一个
极端的办法：反编译代码，修改部分问题。好在源码数量不是很多，如果熟悉Erlang和Otp的一些基础概念，还是能看得懂的。我改的东西不多，
主要还是一些模棱两可的命名，还有写了一些Demo给大家学习。

## 2. 基础教程
JInterface的主要功能就是用Java来模拟ErlangNode节点，实现一个“假装节点”，从而和真正的Erlang节点或者集群通信。所以这个库适用于想
用Java来扩展Erlang的项目，比如用Java实现一些Erlang不支持的驱动，或者是库。当前我主要用在用Java来连接Mongodb4.0以上的版本。
## 3. 一个简单的Demo
官方给出的代码如下所示：
```java

import com.ericsson.otp.erlang.OtpNode;  
import com.ericsson.otp.erlang.OtpMbox;
  
public class ReceiveMessage {  
  
    public static void main(String[] args) throws Exception {  
        OtpNode node = new OtpNode("javaNode1@127.0.0.1");  
  
        OtpMbox mbox = node.createMbox();  
        mbox.registerName("java");  
        System.out.println("开始监控消息");  
        while (true) {  
            System.out.println("收到消息:"+mbox.receive());
        }  
    }  
}  
```
但是上面的代码不利于掌控连接状态，比如中断，中断恢复等等，我简单的改了一下代码，实现了状态监控回调。经过修改的代码如下所示：
```java
package com.ezlinker.otpsupport;

import com.ezlinker.otpsupport.ericsson.otp.erlang.*;

import java.nio.charset.StandardCharsets;

/**
 * OTP Java连接器
 */

public class JavaOtpNode extends Thread {

    /**
     * 连接状态
     * false:连接失败
     * true：连接成功
     */
    private OtpConnectListener otpConnectListener;
    private String nodeName;
    private String selfName;
    private String peerName;
    private String cookie;
    private OtpNode otpNode;
    private OtpMailBox otpMailBox;
    private OtpConnection connection;

    public OtpConnection getConnection() {
        return connection;
    }

    public void setConnection(OtpConnection connection) {
        this.connection = connection;
    }

    public JavaOtpNode(String nodeName, String selfName, String peerName, String cookie) {
        this.nodeName = nodeName;
        this.selfName = selfName;
        this.peerName = peerName;
        this.cookie = cookie;
        setName("JavaOtpNodeThread");
    }

    public void addListener(OtpConnectListener otpConnectListener) {
        this.otpConnectListener = otpConnectListener;
    }

    @Override
    public void run() {
        this.otpNode = null;
        this.otpMailBox = null;
        this.connection = null;

        try {
            this.otpNode = new OtpNode(this.nodeName, this.cookie);

        } catch (Exception e) {
            this.otpConnectListener.onError(e);
            return;
        }
        if (otpNode.ping(this.peerName, 1000)) {
            this.otpConnectListener.onConnect();
            this.otpMailBox = otpNode.createMailBox();
            this.otpMailBox.registerName("ezlinker_core_node1_receive_loop");

            this.otpNode.registerStatusHandler(new OtpNodeStatusListener() {

                @Override
                public void remoteStatus(String node, boolean up, Object info) {
                    System.out.println("远程节点状态: " + node + " " + up + " " + info.toString());
                    if (up) {
                        System.out.println("远程节点开启:" + node);

                    } else {
                        System.out.println("远程节点关闭:" + node);

                    }
                }

                @Override
                public void localStatus(String node, boolean up, Object info) {
                    System.out.println("本地节点状态: " + node + " " + up + " " + info.toString());

                }

                @Override
                public void connAttempt(String node, boolean incoming, Object info) {
                    System.out.println("重新连接 " + node + " " + incoming + " " + info.toString());

                }
            });
            while (true) {
                try {
                    OtpErlangObject erlangObject = this.otpMailBox.receive();
                    System.out.println(erlangObject.toString());
                } catch (Exception e) {
                    this.otpConnectListener.onError(e);
                }
            }
        }
    }
}
```
我在官方的基础上加了一个`OtpNodeStatusListener`来实现状态监控。通过回调实现了连接状态监控：
```java
public abstract class OtpNodeStatusListener {
    public abstract void remoteStatus(String node, boolean up, Object info);

    public abstract void localStatus(String node, boolean up, Object info);

    public abstract void connAttempt(String node, boolean incoming, Object info);
}
```
同时实现了一个简单的看门狗:`OtpNodeSupervisor`，当连接失败的时候，重启连接线程:
```java
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

```
如果在使用过程中有问题可联系本人.