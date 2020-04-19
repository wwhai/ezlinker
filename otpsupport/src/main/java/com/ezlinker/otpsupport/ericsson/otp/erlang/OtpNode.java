
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 此处代码在 2020-3-28 修改过
 * 主要是修复了爱立信一些模棱两可的方法，还有一些JDK6上至今过期的语法等等。
 * 修改人：wwhai
 */
public class OtpNode extends OtpLocalNode {

    /**
     * 连接到的对端
     */
    Hashtable<String, OtpCookedConnection> connections;

    /**
     * 节点的邮箱
     */
    Mailboxes mailboxes;

    /**
     * 事件回调，包含对端节点事件 本地节点事件
     */
    OtpNodeStatusListener otpNodeStatusListener;
    /**
     * 初始化工作完成标记，当连接成功以后，true
     */
    private boolean initDone = false;
    /**
     * Socker接收器
     */
    private Acceptor acceptor;


    private int connFlags = 0;

    public Hashtable<String, OtpCookedConnection> getConnections() {
        return connections;
    }

    public void setConnections(Hashtable<String, OtpCookedConnection> connections) {
        this.connections = connections;
    }

    public Mailboxes getmailboxes() {
        return mailboxes;
    }

    public void setmailboxes(Mailboxes mailboxes) {
        this.mailboxes = mailboxes;
    }


    public boolean isInitDone() {
        return initDone;
    }

    public void setInitDone(boolean initDone) {
        this.initDone = initDone;
    }

    public Acceptor getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    public int getConnFlags() {
        return connFlags;
    }

    public void setConnFlags(int connFlags) {
        this.connFlags = connFlags;
    }

    /* 149 */
    public OtpNode(String node, String cookie) throws IOException {
        this(node, cookie, 0);
    }

    public OtpNode(String node, String cookie, int port) throws IOException {
        /* 194 */
        super(node, cookie);

        /* 196 */
        init(port);

    }


    public OtpNode(String node, String cookie, int port, OtpTransportFactory transportFactory) throws IOException {
        /* 222 */
        super(node, cookie, transportFactory);

        /* 224 */
        init(port);

    }


    private synchronized void init(int aport) throws IOException {
        /* 228 */
        if (!this.initDone) {
            /* 229 */
            this.connections = new Hashtable<String, OtpCookedConnection>(17, 0.95F);

            /* 231 */
            this.mailboxes = new Mailboxes();
            /* 232 */
            this.acceptor = new Acceptor(aport);
            /* 233 */
            this.initDone = true;

        }

    }


    /**
     * 关闭节点,这里可能多个线程同时去关闭，可能抛出异常，所以这里采用了同步锁
     */
    public synchronized void close() {
        /**
         * Socket 连接器退出
         */
        this.acceptor.quit();
        Collection<OtpCookedConnection> cookedConnections = this.connections.values();
        Iterator<OtpCookedConnection> iterator = cookedConnections.iterator();
        /**
         * 进程邮箱清空
         */
        this.mailboxes.clear();
        while (iterator.hasNext()) {
            OtpCookedConnection otpCookedConnection = iterator.next();
            iterator.remove();
            otpCookedConnection.close();

        }
        this.initDone = false;

    }

    /* 271 */
    public OtpMailBox createMailBox() {
        return this.mailboxes.create();
    }


    /* 295 */
    public void closeMailBox(OtpMailBox mailBox) {
        if (mailBox != null) {
            this.mailboxes.remove(mailBox);
            mailBox.name = null;
            mailBox.breakLinks(new OtpErlangAtom("normal"));
        }
    }


    /* 342 */
    public OtpMailBox createMailBox(String name) {
        return this.mailboxes.create(name);
    }


    /* 364 */
    public boolean registerName(String name, OtpMailBox MailBox) {
        return this.mailboxes.register(name, MailBox);
    }


    /* 375 */
    public String[] getNames() {
        return this.mailboxes.names();
    }


    public OtpErlangPid whereis(String name) {
        OtpMailBox mailBox = this.mailboxes.get(name);
        if (mailBox != null) {
            return mailBox.self();
        }
        return null;

    }


    /**
     * 注册一个事件监听器
     *
     * @param otpNodeStatusListener
     */
    public synchronized void registerStatusHandler(OtpNodeStatusListener otpNodeStatusListener) {
        this.otpNodeStatusListener = otpNodeStatusListener;
    }


    public boolean ping(String anode, long timeout) {
        if (anode.equals(this.node))
            return true;
        if (anode.indexOf('@') < 0 && anode.equals(this.node.substring(0, this.node.indexOf('@')))) {
            return true;

        }

        OtpMailBox mailBox = null;

        try {
            mailBox = createMailBox();
            mailBox.send("net_kernel", anode, getPingTuple(mailBox));
            OtpErlangObject reply = mailBox.receive(timeout);
            OtpErlangTuple t = (OtpErlangTuple) reply;
            OtpErlangAtom a = (OtpErlangAtom) t.elementAt(1);
            return "yes".equals(a.atomValue());
        } catch (Exception ignored) {
        } finally {
            closeMailBox(mailBox);
        }
        return false;

    }


    private OtpErlangTuple getPingTuple(OtpMailBox MailBox) {
        OtpErlangObject[] ping = new OtpErlangObject[3];
        OtpErlangObject[] pid = new OtpErlangObject[2];
        OtpErlangObject[] anode = new OtpErlangObject[2];
        pid[0] = MailBox.self();
        pid[1] = createRef();
        anode[0] = new OtpErlangAtom("is_auth");
        anode[1] = new OtpErlangAtom(node());
        ping[0] = new OtpErlangAtom("$gen_call");
        ping[1] = new OtpErlangTuple(pid);
        ping[2] = new OtpErlangTuple(anode);
        return new OtpErlangTuple(ping);
    }


    private boolean netKernel(OtpMsg m) {
        OtpMailBox mailBox = null;

        /* 497 */
        try {
            OtpErlangTuple t = (OtpErlangTuple) m.getMsg();
            /* 498 */
            OtpErlangTuple req = (OtpErlangTuple) t.elementAt(1);


            /* 501 */
            OtpErlangPid pid = (OtpErlangPid) req.elementAt(0);


            /* 504 */
            OtpErlangObject[] pong = new OtpErlangObject[2];
            /* 505 */
            pong[0] = req.elementAt(1);
            /* 506 */
            pong[1] = new OtpErlangAtom("yes");

            /* 508 */
            mailBox = createMailBox();
            /* 509 */
            mailBox.send(pid, new OtpErlangTuple(pong));
            /* 510 */
            return true;
        }
        /* 511 */ catch (Exception exception) {
        } finally
            /* 513 */ {
            closeMailBox(mailBox);
        }

        /* 515 */
        return false;

    }


    boolean deliver(OtpMsg m) {
        /* 523 */
        OtpMailBox mailBox = null;


        try {
            /* 526 */
            int t = m.type();

            /* 528 */
            if (t == 6) {
                /* 529 */
                String name = m.getRecipientName();

                /* 531 */
                if (name.equals("net_kernel")) {
                    /* 532 */
                    return netKernel(m);

                }
                /* 534 */
                mailBox = this.mailboxes.get(name);

            } else {
                /* 536 */
                mailBox = this.mailboxes.get(m.getRecipientPid());

            }

            /* 539 */
            if (mailBox == null) {
                /* 540 */
                return false;

            }
            /* 542 */
            mailBox.deliver(m);
            /* 543 */
        } catch (Exception e) {
            /* 544 */
            return false;

        }

        /* 547 */
        return true;

    }


    void deliverError(OtpCookedConnection conn, Exception e) {
        /* 555 */
        removeConnection(conn);
        /* 556 */
        remoteStatus(conn.name, false, e);

    }


    OtpCookedConnection getConnection(String anode) {
        /* 563 */
        OtpPeer peer;
        /* 564 */
        OtpCookedConnection connection;

        /* 566 */
        synchronized (this.connections) {

            /* 568 */
            connection = this.connections.get(anode);

            /* 570 */
            if (connection == null) {

                /* 572 */
                peer = new OtpPeer(anode);
                /* 573 */
                connection = this.connections.get(peer.node());

                /* 575 */
                if (connection == null) {

                    try {
                        /* 577 */
                        connection = new OtpCookedConnection(this, peer);
                        /* 578 */
                        connection.setFlags(this.connFlags);
                        /* 579 */
                        addConnection(connection);
                        /* 580 */
                    } catch (Exception e) {

                        /* 582 */
                        connAttempt(peer.node(), false, e);

                    }

                }

            }
            return connection;
        }

    }


    void addConnection(OtpCookedConnection connection) {
        if (connection != null && connection.name != null) {

            this.connections.put(connection.name, connection);

            remoteStatus(connection.name, true, null);

        }

    }


    private void removeConnection(OtpCookedConnection conn) {
        /* 598 */
        if (conn != null && conn.name != null) {
            /* 599 */
            this.connections.remove(conn.name);

        }

    }

    /**
     * 回调：当远端节点状态改变的时候这里触发
     *
     * @param anode
     * @param up
     * @param info
     */
    private synchronized void remoteStatus(String anode, boolean up, Object info) {
        if (this.otpNodeStatusListener == null) {
            return;
        }
        this.otpNodeStatusListener.remoteStatus(anode, up, info);

    }

    /**
     * 本地状态改变
     *
     * @param anode
     * @param up
     * @param info
     */
    synchronized void localStatus(String anode, boolean up, Object info) {
        if (this.otpNodeStatusListener == null) {
            return;
        }
        this.otpNodeStatusListener.localStatus(anode, up, info);
    }

    /**
     * 重新连接的时候调用
     *
     * @param anode
     * @param incoming
     * @param info
     */
    synchronized void connAttempt(String anode, boolean incoming, Object info) {
        /* 628 */
        if (this.otpNodeStatusListener == null) {
            return;
        }
        this.otpNodeStatusListener.connAttempt(anode, incoming, info);
    }


    /* 884 */
    public void setFlags(int flags) {
        this.connFlags = flags;
    }


    public class Mailboxes {
        private Hashtable<OtpErlangPid, WeakReference<OtpMailBox>> byPid;

        private Hashtable<String, WeakReference<OtpMailBox>> byName;


        public Mailboxes() {
            /* 648 */
            this.byPid = new Hashtable<>(17, 0.95F);

            /* 650 */
            this.byName = new Hashtable<>(17, 0.95F);

        }

        public OtpMailBox create(String name) {
            /* 655 */
            OtpMailBox m;

            /* 657 */
            synchronized (this.byName) {
                /* 658 */
                if (get(name) != null) {
                    /* 659 */
                    return null;

                }
                /* 661 */
                OtpErlangPid pid = OtpNode.this.createPid();
                /* 662 */
                m = new OtpMailBox(OtpNode.this, pid, name);
                /* 663 */
                this.byPid.put(pid, new WeakReference<OtpMailBox>(m));
                /* 664 */
                this.byName.put(name, new WeakReference<OtpMailBox>(m));

            }
            /* 666 */
            return m;

        }


        public OtpMailBox create() {
            /* 670 */
            OtpErlangPid pid = OtpNode.this.createPid();
            /* 671 */
            OtpMailBox m = new OtpMailBox(OtpNode.this, pid);
            /* 672 */
            this.byPid.put(pid, new WeakReference<OtpMailBox>(m));
            /* 673 */
            return m;

        }


        public void clear() {
            /* 677 */
            this.byPid.clear();
            /* 678 */
            this.byName.clear();

        }


        public String[] names() {
            /* 682 */
            String[] allnames = null;

            /* 684 */
            synchronized (this.byName) {
                /* 685 */
                int n = this.byName.size();
                /* 686 */
                Enumeration<String> keys = this.byName.keys();
                /* 687 */
                allnames = new String[n];

                /* 689 */
                int i = 0;
                /* 690 */
                while (keys.hasMoreElements()) {
                    /* 691 */
                    allnames[i++] = keys.nextElement();

                }

            }
            /* 694 */
            return allnames;

        }


        public boolean register(String name, OtpMailBox MailBox) {
            /* 698 */
            if (name == null) {
                /* 699 */
                if (MailBox.name != null) {
                    /* 700 */
                    this.byName.remove(MailBox.name);
                    /* 701 */
                    MailBox.name = null;

                }

            } else {
                /* 704 */
                synchronized (this.byName) {
                    /* 705 */
                    if (get(name) != null) {
                        /* 706 */
                        return false;

                    }
                    /* 708 */
                    this.byName.put(name, new WeakReference<OtpMailBox>(MailBox));
                    /* 709 */
                    MailBox.name = name;

                }

            }
            /* 712 */
            return true;

        }


        public OtpMailBox get(String name) {
            /* 721 */
            WeakReference<OtpMailBox> wr = this.byName.get(name);

            /* 723 */
            if (wr != null) {
                /* 724 */
                OtpMailBox m = wr.get();

                /* 726 */
                if (m != null) {
                    /* 727 */
                    return m;

                }
                /* 729 */
                this.byName.remove(name);

            }
            /* 731 */
            return null;

        }


        public OtpMailBox get(OtpErlangPid pid) {
            /* 740 */
            WeakReference<OtpMailBox> wr = this.byPid.get(pid);

            /* 742 */
            if (wr != null) {
                /* 743 */
                OtpMailBox m = wr.get();

                /* 745 */
                if (m != null) {
                    /* 746 */
                    return m;

                }
                /* 748 */
                this.byPid.remove(pid);

            }
            /* 750 */
            return null;

        }


        public void remove(OtpMailBox MailBox) {
            /* 754 */
            this.byPid.remove(MailBox.self);
            /* 755 */
            if (MailBox.name != null) {
                /* 756 */
                this.byName.remove(MailBox.name);

            }

        }

    }


    /**
     * 此处主要负责Socket连接的维护
     */
    public class Acceptor extends Thread {
        private final OtpServerTransport sock;
        private final int acceptorPort;
        private volatile boolean done = false;


        Acceptor(int port) throws IOException {
            this.sock = OtpNode.this.createServerTransport(port);
            this.acceptorPort = this.sock.getLocalPort();
            OtpNode.this.port = this.acceptorPort;
            setDaemon(true);
            setName("acceptor");
            publishPort();
            start();

        }


        private void publishPort() throws IOException {
            if (OtpNode.this.getEpmd() != null) {
                return;
            }
            OtpEpmd.publishPort(OtpNode.this);

        }


        private void unPublishPort() {
            OtpEpmd.unPublishPort(OtpNode.this);
            closeSock(OtpNode.this.epmd);
            OtpNode.this.epmd = null;
        }


        public void quit() {
            unPublishPort();
            this.done = true;
            closeSock(this.sock);
            OtpNode.this.localStatus(OtpNode.this.node, false, null);
        }


        private void closeSock(OtpServerTransport s) {

            try {
                if (s != null) {
                    s.close();
                }
            } catch (Exception ignored) {
            }
        }


        private void closeSock(OtpTransport s) {

            try {
                if (s != null) {
                    s.close();
                }
            } catch (Exception ignored) {
            }

        }

        public int port() {
            return this.acceptorPort;
        }

        @Override
        public void run() {
            OtpTransport otpTransport;
            OtpCookedConnection otpCookedConnection;
            OtpNode.this.localStatus(OtpNode.this.node, true, null);
            while (!this.done) {
                /* 834 */
                try {
                    otpTransport = this.sock.accept();
                } catch (Exception e) {
                    if (!this.done) {
                        OtpNode.this.localStatus(OtpNode.this.node, false, e);
                    }
                    break;
                }
                try {
                    synchronized (OtpNode.this.connections) {
                        otpCookedConnection = new OtpCookedConnection(OtpNode.this, otpTransport);
                        otpCookedConnection.setFlags(OtpNode.this.connFlags);
                        OtpNode.this.addConnection(otpCookedConnection);

                    }
                } catch (OtpAuthException | IOException e) {
                    OtpNode.this.connAttempt("unknown", true, e);
                    closeSock(otpTransport);
                } catch (Exception e) {
                    closeSock(otpTransport);
                    closeSock(this.sock);
                    OtpNode.this.localStatus(OtpNode.this.node, false, e);
                    break;
                }

            }
            unPublishPort();
        }

    }

}
