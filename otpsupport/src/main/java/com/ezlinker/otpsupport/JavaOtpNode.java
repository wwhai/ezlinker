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
                    covertErlangObject(erlangObject);
                } catch (Exception e) {
                    this.otpConnectListener.onError(e);
                }
            }
        }
    }

    /**
     * Erlang数据类型转换
     *
     * @param otpErlangObject
     */
    private void covertErlangObject(OtpErlangObject otpErlangObject) throws OtpErlangRangeException {
        /**
         * { { id , qos, from , flags , headers , topic , payload , timestamp }}
         */

        if (otpErlangObject instanceof OtpErlangTuple) {
            OtpErlangTuple tuple = (OtpErlangTuple) otpErlangObject;
            if (tuple.arity() == 1) {
                if (tuple.elementAt(0) instanceof OtpErlangTuple) {
                    OtpErlangTuple body = (OtpErlangTuple) tuple.elementAt(0);
                    if (body.arity() == 9) {
                        OtpErlangAtom name = (OtpErlangAtom) body.elementAt(0);
                        if (name.atomValue().equalsIgnoreCase("message")) {
                            OtpErlangBinary topicBinary = (OtpErlangBinary) body.elementAt(6);
                            OtpErlangBinary payloadBinary = (OtpErlangBinary) body.elementAt(7);
                            String payloadString = new String(payloadBinary.binaryValue(), StandardCharsets.UTF_8);
                            String topicString = new String(topicBinary.binaryValue(), StandardCharsets.UTF_8);
                            this.otpConnectListener.onData(topicString, payloadString);
                        }
                    }
                }

            }
        }
    }

    public OtpConnectListener getOtpConnectListener() {
        return otpConnectListener;
    }

    public void setOtpConnectListener(OtpConnectListener otpConnectListener) {
        this.otpConnectListener = otpConnectListener;
    }

    public String getSelfName() {
        return selfName;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public OtpNode getOtpNode() {
        return otpNode;
    }

    public void setOtpNode(OtpNode otpNode) {
        this.otpNode = otpNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public OtpMailBox getOtpMailBox() {
        return otpMailBox;
    }

    public void setOtpMailBox(OtpMailBox otpMailBox) {
        this.otpMailBox = otpMailBox;
    }
}
