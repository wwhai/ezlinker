//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ezlinker.otpsupport.ericsson.otp.erlang;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public abstract class AbstractConnection extends Thread {
    protected static final int headerLen = 2048;
    protected static final byte passThrough = 112;
    protected static final byte version = -125;
    protected static final int linkTag = 1;
    protected static final int sendTag = 2;
    protected static final int exitTag = 3;
    protected static final int unlinkTag = 4;
    protected static final int regSendTag = 6;
    protected static final int groupLeaderTag = 7;
    protected static final int exit2Tag = 8;
    protected static final int sendTTTag = 12;
    protected static final int exitTTTag = 13;
    protected static final int regSendTTTag = 16;
    protected static final int exit2TTTag = 18;
    protected static final int ChallengeReply = 114;
    protected static final int ChallengeAck = 97;
    protected static final int ChallengeStatus = 115;
    protected static int defaultLevel = 0;
    protected static int sendThreshold = 1;
    protected static int ctrlThreshold = 2;
    protected static int handshakeThreshold = 3;
    protected static Random random = null;

    static {
        String trace = System.getProperties().getProperty("OtpConnection.trace");

        try {
            if (trace != null) {
                defaultLevel = Integer.valueOf(trace);
            }
        } catch (NumberFormatException var2) {
            defaultLevel = 0;
        }

        random = new Random();
    }

    protected boolean connected = false;
    protected OtpTransport socket;
    protected OtpPeer peer;
    protected OtpLocalNode localNode;
    protected boolean cookieOk = false;
    protected boolean sendCookie = true;
    protected int traceLevel = 0;
    String name;
    private volatile boolean done = false;
    private int flags = 0;

    protected AbstractConnection(OtpLocalNode self, OtpTransport s) throws IOException, OtpAuthException {
        this.localNode = self;
        this.peer = new OtpPeer(self.transportFactory);
        this.socket = s;
        this.traceLevel = defaultLevel;
        this.setDaemon(true);
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- ACCEPT FROM " + s);
        }

        this.recvName(this.peer);
        if (this.peer.proto == self.proto && self.distHigh >= this.peer.distLow && self.distLow <= this.peer.distHigh) {
            this.peer.distChoose = this.peer.distHigh > self.distHigh ? self.distHigh : this.peer.distHigh;
            this.doAccept();
            this.name = this.peer.node();
        } else {
            this.close();
            throw new IOException("No common protocol found - cannot accept connection");
        }
    }

    protected AbstractConnection(OtpLocalNode self, OtpPeer other) throws IOException, OtpAuthException {
        this.peer = other;
        this.localNode = self;
        this.socket = null;
        this.traceLevel = defaultLevel;
        this.setDaemon(true);
        int port = OtpEpmd.lookupPort(this.peer);
        if (port == 0) {
            throw new IOException("No remote node found - cannot connect");
        } else if (this.peer.proto == self.proto && self.distHigh >= this.peer.distLow && self.distLow <= this.peer.distHigh) {
            this.peer.distChoose = this.peer.distHigh > self.distHigh ? self.distHigh : this.peer.distHigh;
            this.doConnect(port);
            this.name = this.peer.node();
            this.connected = true;
        } else {
            throw new IOException("No common protocol found - cannot connect");
        }
    }

    protected static int genChallenge() {
        return random.nextInt();
    }

    static String hex0(byte x) {
        char[] tab = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int uint;
        if (x < 0) {
            uint = x & 127;
            uint |= 128;
        } else {
            uint = x;
        }

        return "" + tab[uint >>> 4] + tab[uint & 15];
    }

    static String hex(byte[] b) {
        StringBuffer sb = new StringBuffer();

        try {
            for (int i = 0; i < b.length; ++i) {
                sb.append(hex0(b[i]));
            }
        } catch (Exception var3) {
        }

        return sb.toString();
    }

    public abstract void deliver(Exception var1);

    public abstract void deliver(OtpMsg var1);

    protected void sendBuf(OtpErlangPid from, String dest, OtpOutputStream payload) throws IOException {
        if (!this.connected) {
            throw new IOException("Not connected");
        } else {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(4);
            header.write_long(6L);
            header.write_any(from);
            if (this.sendCookie) {
                header.write_atom(this.localNode.cookie());
            } else {
                header.write_atom("");
            }

            header.write_atom(dest);
            header.write1(-125L);
            header.poke4BE(0, (long) (header.size() + payload.size() - 4));
            this.do_send(header, payload);
        }
    }

    protected void sendBuf(OtpErlangPid from, OtpErlangPid dest, OtpOutputStream payload) throws IOException {
        if (!this.connected) {
            throw new IOException("Not connected");
        } else {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(3);
            header.write_long(2L);
            if (this.sendCookie) {
                header.write_atom(this.localNode.cookie());
            } else {
                header.write_atom("");
            }

            header.write_any(dest);
            header.write1(-125L);
            header.poke4BE(0, (long) (header.size() + payload.size() - 4));
            this.do_send(header, payload);
        }
    }

    private void cookieError(OtpLocalNode local, OtpErlangAtom cookie) throws OtpAuthException {
        try {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(4);
            header.write_long(6L);
            header.write_any(local.createPid());
            header.write_atom(cookie.atomValue());
            header.write_atom("auth");
            header.write1(-125L);
            OtpErlangObject[] msg = new OtpErlangObject[2];
            OtpErlangObject[] msgbody = new OtpErlangObject[]{new OtpErlangAtom("print"), new OtpErlangString("~n** Bad cookie sent to " + local + " **~n"), new OtpErlangList()};
            msg[0] = new OtpErlangAtom("$gen_cast");
            msg[1] = new OtpErlangTuple(msgbody);
            OtpOutputStream payload = new OtpOutputStream(new OtpErlangTuple(msg));
            header.poke4BE(0, (long) (header.size() + payload.size() - 4));

            try {
                this.do_send(header, payload);
            } catch (IOException var11) {
            }
        } finally {
            this.close();
        }

        throw new OtpAuthException("Remote cookie not authorized: " + cookie.atomValue());
    }

    protected void sendLink(OtpErlangPid from, OtpErlangPid dest) throws IOException {
        if (!this.connected) {
            throw new IOException("Not connected");
        } else {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(3);
            header.write_long(1L);
            header.write_any(from);
            header.write_any(dest);
            header.poke4BE(0, (long) (header.size() - 4));
            this.do_send(header);
        }
    }

    protected void sendUnlink(OtpErlangPid from, OtpErlangPid dest) throws IOException {
        if (!this.connected) {
            throw new IOException("Not connected");
        } else {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(3);
            header.write_long(4L);
            header.write_any(from);
            header.write_any(dest);
            header.poke4BE(0, (long) (header.size() - 4));
            this.do_send(header);
        }
    }

    protected void sendExit(OtpErlangPid from, OtpErlangPid dest, OtpErlangObject reason) throws IOException {
        this.sendExit(3, from, dest, reason);
    }

    protected void sendExit2(OtpErlangPid from, OtpErlangPid dest, OtpErlangObject reason) throws IOException {
        this.sendExit(8, from, dest, reason);
    }

    private void sendExit(int tag, OtpErlangPid from, OtpErlangPid dest, OtpErlangObject reason) throws IOException {
        if (!this.connected) {
            throw new IOException("Not connected");
        } else {
            OtpOutputStream header = new OtpOutputStream(2048);
            header.write4BE(0L);
            header.write1(112L);
            header.write1(-125L);
            header.write_tuple_head(4);
            header.write_long((long) tag);
            header.write_any(from);
            header.write_any(dest);
            header.write_any(reason);
            header.poke4BE(0, (long) (header.size() - 4));
            this.do_send(header);
        }
    }

    public void run() {
        if (!this.connected) {
            this.deliver((Exception) (new IOException("Not connected")));
        } else {
            byte[] lbuf = new byte[4];
            byte[] tock = new byte[]{0, 0, 0, 0};

            try {
                label286:
                while (!this.done) {
                    OtpInputStream ibuf;
                    int len;
                    OutputStream reason;
                    do {
                        this.readSock(this.socket, lbuf);
                        ibuf = new OtpInputStream(lbuf, this.flags);
                        len = ibuf.read4BE();
                        if (len == 0) {
                            synchronized (this) {
                                reason = this.socket.getOutputStream();
                                reason.write(tock);
                                reason.flush();
                            }
                        }
                    } while (len == 0);

                    byte[] tmpbuf = new byte[len];
                    this.readSock(this.socket, tmpbuf);
                    ibuf.close();
                    ibuf = new OtpInputStream(tmpbuf, this.flags);
                    if (ibuf.read1() != 112) {
                        break;
                    }

                    reason = null;
                    OtpErlangAtom cookie = null;
                    OtpErlangObject tmp = null;
                    OtpErlangTuple head = null;
                    tmp = ibuf.read_any();
                    if (!(tmp instanceof OtpErlangTuple)) {
                        break;
                    }

                    head = (OtpErlangTuple) tmp;
                    if (!(head.elementAt(0) instanceof OtpErlangLong)) {
                        break;
                    }

                    int tag = (int) ((OtpErlangLong) head.elementAt(0)).longValue();
                    OtpErlangObject traceobj;
                    OtpErlangPid to;
                    OtpErlangPid from;
                    OtpErlangObject reasonObject;
                    switch (tag) {
                        case 1:
                        case 4:
                            if (this.traceLevel >= ctrlThreshold) {
                                System.out.println("<- " + this.headerType(head) + " " + head);
                            }

                            from = (OtpErlangPid) head.elementAt(1);
                            to = (OtpErlangPid) head.elementAt(2);
                            this.deliver(new OtpMsg(tag, from, to));
                            break;
                        case 2:
                        case 12:
                            if (!this.cookieOk) {
                                if (!(head.elementAt(1) instanceof OtpErlangAtom)) {
                                    break label286;
                                }

                                cookie = (OtpErlangAtom) head.elementAt(1);
                                if (this.sendCookie) {
                                    if (!cookie.atomValue().equals(this.localNode.cookie())) {
                                        this.cookieError(this.localNode, cookie);
                                    }
                                } else if (!cookie.atomValue().equals("")) {
                                    this.cookieError(this.localNode, cookie);
                                }

                                this.cookieOk = true;
                            }

                            if (this.traceLevel >= sendThreshold) {
                                System.out.println("<- " + this.headerType(head) + " " + head);
                                ibuf.mark(0);
                                traceobj = ibuf.read_any();
                                if (traceobj != null) {
                                    System.out.println("   " + traceobj);
                                } else {
                                    System.out.println("   (null)");
                                }

                                ibuf.reset();
                            }

                            to = (OtpErlangPid) head.elementAt(2);
                            this.deliver(new OtpMsg(to, ibuf));
                            break;
                        case 3:
                        case 8:
                            if (head.elementAt(3) != null) {
                                if (this.traceLevel >= ctrlThreshold) {
                                    System.out.println("<- " + this.headerType(head) + " " + head);
                                }

                                from = (OtpErlangPid) head.elementAt(1);
                                to = (OtpErlangPid) head.elementAt(2);
                                reasonObject = head.elementAt(3);
                                this.deliver(new OtpMsg(tag, from, to, reasonObject));
                                break;
                            }
                        case 5:
                        case 9:
                        case 10:
                        case 11:
                        case 14:
                        case 15:
                        case 17:
                        default:
                            break label286;
                        case 6:
                        case 16:
                            if (!this.cookieOk) {
                                if (!(head.elementAt(2) instanceof OtpErlangAtom)) {
                                    break label286;
                                }

                                cookie = (OtpErlangAtom) head.elementAt(2);
                                if (this.sendCookie) {
                                    if (!cookie.atomValue().equals(this.localNode.cookie())) {
                                        this.cookieError(this.localNode, cookie);
                                    }
                                } else if (!cookie.atomValue().equals("")) {
                                    this.cookieError(this.localNode, cookie);
                                }

                                this.cookieOk = true;
                            }

                            if (this.traceLevel >= sendThreshold) {
                                System.out.println("<- " + this.headerType(head) + " " + head);
                                ibuf.mark(0);
                                traceobj = ibuf.read_any();
                                if (traceobj != null) {
                                    System.out.println("   " + traceobj);
                                } else {
                                    System.out.println("   (null)");
                                }

                                ibuf.reset();
                            }

                            from = (OtpErlangPid) head.elementAt(1);
                            OtpErlangAtom toName = (OtpErlangAtom) head.elementAt(3);
                            this.deliver(new OtpMsg(from, toName.atomValue(), ibuf));
                            break;
                        case 7:
                            if (this.traceLevel >= ctrlThreshold) {
                                System.out.println("<- " + this.headerType(head) + " " + head);
                            }
                            break;
                        case 13:
                        case 18:
                            if (head.elementAt(4) == null) {
                                break label286;
                            }

                            if (this.traceLevel >= ctrlThreshold) {
                                System.out.println("<- " + this.headerType(head) + " " + head);
                            }

                            from = (OtpErlangPid) head.elementAt(1);
                            to = (OtpErlangPid) head.elementAt(2);
                            reasonObject = head.elementAt(4);
                            this.deliver(new OtpMsg(tag, from, to, reasonObject));
                    }
                }

                this.deliver((Exception) (new OtpErlangExit("Remote is sending garbage")));
            } catch (OtpAuthException var22) {
                this.deliver((Exception) var22);
            } catch (OtpErlangDecodeException var23) {
                this.deliver((Exception) (new OtpErlangExit("Remote is sending garbage")));
            } catch (IOException var24) {
                this.deliver((Exception) (new OtpErlangExit("Remote has closed connection")));
            } finally {
                this.close();
            }

        }
    }

    public int setTraceLevel(int level) {
        int oldLevel = this.traceLevel;
        int theLevel = level;
        if (level < 0) {
            theLevel = 0;
        } else if (level > 4) {
            theLevel = 4;
        }

        this.traceLevel = theLevel;
        return oldLevel;
    }

    public int getTraceLevel() {
        return this.traceLevel;
    }

    public void close() {
        this.done = true;
        this.connected = false;
        synchronized (this) {
            try {
                if (this.socket != null) {
                    if (this.traceLevel >= ctrlThreshold) {
                        System.out.println("-> CLOSE");
                    }

                    this.socket.close();
                }
            } catch (IOException var8) {
            } finally {
                this.socket = null;
            }

        }
    }

    protected void finalize() {
        this.close();
    }

    public boolean isConnected() {
        return this.connected;
    }

    protected synchronized void do_send(OtpOutputStream header, OtpOutputStream payload) throws IOException {
        try {
            if (this.traceLevel >= sendThreshold) {
                try {
                    OtpErlangObject h = header.getOtpInputStream(5).read_any();
                    System.out.println("-> " + this.headerType(h) + " " + h);
                    OtpErlangObject o = payload.getOtpInputStream(0).read_any();
                    System.out.println("   " + o);
                    o = null;
                } catch (OtpErlangDecodeException var5) {
                    System.out.println("   can't decode output buffer:" + var5);
                }
            }

            OutputStream out = this.socket.getOutputStream();
            header.writeTo(out);
            payload.writeTo(out);
            out.flush();
        } catch (IOException var6) {
            this.close();
            throw var6;
        }
    }

    protected synchronized void do_send(OtpOutputStream header) throws IOException {
        try {
            if (this.traceLevel >= ctrlThreshold) {
                try {
                    OtpErlangObject h = header.getOtpInputStream(5).read_any();
                    System.out.println("-> " + this.headerType(h) + " " + h);
                } catch (OtpErlangDecodeException var3) {
                    System.out.println("   can't decode output buffer: " + var3);
                }
            }

            header.writeToAndFlush(this.socket.getOutputStream());
        } catch (IOException var4) {
            this.close();
            throw var4;
        }
    }

    protected String headerType(OtpErlangObject h) {
        int tag = -1;
        if (h instanceof OtpErlangTuple) {
            tag = (int) ((OtpErlangLong) ((OtpErlangTuple) h).elementAt(0)).longValue();
        }

        switch (tag) {
            case 1:
                return "LINK";
            case 2:
                return "SEND";
            case 3:
                return "EXIT";
            case 4:
                return "UNLINK";
            case 5:
            case 9:
            case 10:
            case 11:
            case 14:
            case 15:
            case 17:
            default:
                return "(unknown type)";
            case 6:
                return "REG_SEND";
            case 7:
                return "GROUP_LEADER";
            case 8:
                return "EXIT2";
            case 12:
                return "SEND_TT";
            case 13:
                return "EXIT_TT";
            case 16:
                return "REG_SEND_TT";
            case 18:
                return "EXIT2_TT";
        }
    }

    protected int readSock(OtpTransport s, byte[] b) throws IOException {
        int got = 0;
        int len = b.length;
        synchronized (this) {
            if (s == null) {
                throw new IOException("expected " + len + " bytes, socket was closed");
            }
        }

        while (got < len) {
            int i = s.getInputStream().read(b, got, len - got);
            if (i < 0) {
                throw new IOException("expected " + len + " bytes, got EOF after " + got + " bytes");
            }

            if (i == 0 && len != 0) {
                throw new IOException("Remote connection closed");
            }

            got += i;
        }

        return got;
    }

    protected void doAccept() throws IOException, OtpAuthException {
        try {
            this.sendStatus("ok");
            int our_challenge = genChallenge();
            this.sendChallenge(this.peer.distChoose, this.localNode.flags, our_challenge);
            int her_challenge = this.recvChallengeReply(our_challenge);
            byte[] our_digest = this.genDigest(her_challenge, this.localNode.cookie());
            this.sendChallengeAck(our_digest);
            this.connected = true;
            this.cookieOk = true;
            this.sendCookie = false;
        } catch (IOException var4) {
            this.close();
            throw var4;
        } catch (OtpAuthException var5) {
            this.close();
            throw var5;
        } catch (Exception var6) {
            String nn = this.peer.node();
            this.close();
            IOException ioe = new IOException("Error accepting connection from " + nn);
            ioe.initCause(var6);
            throw ioe;
        }

        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- MD5 ACCEPTED " + this.peer.host());
        }

    }

    protected void doConnect(int port) throws IOException, OtpAuthException {
        try {
            this.socket = this.peer.createTransport(this.peer.host(), port);
            if (this.traceLevel >= handshakeThreshold) {
                System.out.println("-> MD5 CONNECT TO " + this.peer.host() + ":" + port);
            }

            this.sendName(this.peer.distChoose, this.localNode.flags);
            this.recvStatus();
            int her_challenge = this.recvChallenge();
            byte[] our_digest = this.genDigest(her_challenge, this.localNode.cookie());
            int our_challenge = genChallenge();
            this.sendChallengeReply(our_challenge, our_digest);
            this.recvChallengeAck(our_challenge);
            this.cookieOk = true;
            this.sendCookie = false;
        } catch (OtpAuthException var5) {
            this.close();
            throw var5;
        } catch (Exception var6) {
            this.close();
            IOException ioe = new IOException("Cannot connect to peer node");
            ioe.initCause(var6);
            throw ioe;
        }
    }

    protected byte[] genDigest(int challenge, String cookie) {
        long ch2;
        if (challenge < 0) {
            ch2 = 2147483648L;
            ch2 |= (long) (challenge & 2147483647);
        } else {
            ch2 = (long) challenge;
        }

        OtpMD5 context = new OtpMD5();
        context.update(cookie);
        context.update("" + ch2);
        int[] tmp = context.final_bytes();
        byte[] res = new byte[tmp.length];

        for (int i = 0; i < tmp.length; ++i) {
            res[i] = (byte) (tmp[i] & 255);
        }

        return res;
    }

    protected void sendName(int dist, int aflags) throws IOException {
        OtpOutputStream obuf = new OtpOutputStream();
        String str = this.localNode.node();
        obuf.write2BE((long) (str.length() + 7));
        obuf.write1(110L);
        obuf.write2BE((long) dist);
        obuf.write4BE((long) aflags);
        obuf.write(str.getBytes());
        obuf.writeToAndFlush(this.socket.getOutputStream());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("-> HANDSHAKE sendName flags=" + aflags + " dist=" + dist + " local=" + this.localNode);
        }

    }

    protected void sendChallenge(int dist, int aflags, int challenge) throws IOException {
        OtpOutputStream obuf = new OtpOutputStream();
        String str = this.localNode.node();
        obuf.write2BE((long) (str.length() + 11));
        obuf.write1(110L);
        obuf.write2BE((long) dist);
        obuf.write4BE((long) aflags);
        obuf.write4BE((long) challenge);
        obuf.write(str.getBytes());
        obuf.writeToAndFlush(this.socket.getOutputStream());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("-> HANDSHAKE sendChallenge flags=" + aflags + " dist=" + dist + " challenge=" + challenge + " local=" + this.localNode);
        }

    }

    protected byte[] read2BytePackage() throws IOException, OtpErlangDecodeException {
        byte[] lbuf = new byte[2];
        this.readSock(this.socket, lbuf);
        OtpInputStream ibuf = new OtpInputStream(lbuf, 0);
        int len = ibuf.read2BE();
        byte[] tmpbuf = new byte[len];
        this.readSock(this.socket, tmpbuf);
        return tmpbuf;
    }

    protected void recvName(OtpPeer apeer) throws IOException {
        String hisname = "";

        try {
            byte[] tmpbuf = this.read2BytePackage();
            OtpInputStream ibuf = new OtpInputStream(tmpbuf, 0);
            int len = tmpbuf.length;
            apeer.ntype = ibuf.read1();
            if (apeer.ntype != 110) {
                throw new IOException("Unknown remote node type");
            }

            apeer.distLow = apeer.distHigh = ibuf.read2BE();
            if (apeer.distLow < 5) {
                throw new IOException("Unknown remote node type");
            }

            apeer.flags = ibuf.read4BE();
            byte[] tmpname = new byte[len - 7];
            ibuf.readN(tmpname);
            hisname = OtpErlangString.newString(tmpname);
            if ((apeer.flags & 1) != 0) {
                apeer.ntype = 109;
            } else {
                apeer.ntype = 104;
            }

            if ((apeer.flags & 4) == 0) {
                throw new IOException("Handshake failed - peer cannot handle extended references");
            }

            if ((apeer.flags & 256) == 0) {
                throw new IOException("Handshake failed - peer cannot handle extended pids and ports");
            }
        } catch (OtpErlangDecodeException var7) {
            throw new IOException("Handshake failed - not enough data");
        }

        int i = hisname.indexOf(64, 0);
        apeer.node = hisname;
        apeer.alive = hisname.substring(0, i);
        apeer.host = hisname.substring(i + 1, hisname.length());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- HANDSHAKE ntype=" + apeer.ntype + " dist=" + apeer.distHigh + " remote=" + apeer);
        }

    }

    protected int recvChallenge() throws IOException {
        int challenge;
        try {
            byte[] buf = this.read2BytePackage();
            OtpInputStream ibuf = new OtpInputStream(buf, 0);
            this.peer.ntype = ibuf.read1();
            if (this.peer.ntype != 110) {
                throw new IOException("Unexpected peer type");
            }

            this.peer.distLow = this.peer.distHigh = ibuf.read2BE();
            this.peer.flags = ibuf.read4BE();
            challenge = ibuf.read4BE();
            byte[] tmpname = new byte[buf.length - 11];
            ibuf.readN(tmpname);
            String hisname = OtpErlangString.newString(tmpname);
            if (!hisname.equals(this.peer.node)) {
                throw new IOException("Handshake failed - peer has wrong name: " + hisname);
            }

            if ((this.peer.flags & 4) == 0) {
                throw new IOException("Handshake failed - peer cannot handle extended references");
            }

            if ((this.peer.flags & 256) == 0) {
                throw new IOException("Handshake failed - peer cannot handle extended pids and ports");
            }
        } catch (OtpErlangDecodeException var6) {
            throw new IOException("Handshake failed - not enough data");
        }

        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- HANDSHAKE recvChallenge from=" + this.peer.node + " challenge=" + challenge + " local=" + this.localNode);
        }

        return challenge;
    }

    protected void sendChallengeReply(int challenge, byte[] digest) throws IOException {
        OtpOutputStream obuf = new OtpOutputStream();
        obuf.write2BE(21L);
        obuf.write1(114L);
        obuf.write4BE((long) challenge);
        obuf.write(digest);
        obuf.writeToAndFlush(this.socket.getOutputStream());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("-> HANDSHAKE sendChallengeReply challenge=" + challenge + " digest=" + hex(digest) + " local=" + this.localNode);
        }

    }

    private boolean digests_equals(byte[] a, byte[] b) {
        for (int i = 0; i < 16; ++i) {
            if (a[i] != b[i]) {
                return false;
            }
        }

        return true;
    }

    protected int recvChallengeReply(int our_challenge) throws IOException, OtpAuthException {
        byte[] her_digest = new byte[16];

        int challenge;
        try {
            byte[] buf = this.read2BytePackage();
            OtpInputStream ibuf = new OtpInputStream(buf, 0);
            int tag = ibuf.read1();
            if (tag != 114) {
                throw new IOException("Handshake protocol error");
            }

            challenge = ibuf.read4BE();
            ibuf.readN(her_digest);
            byte[] our_digest = this.genDigest(our_challenge, this.localNode.cookie());
            if (!this.digests_equals(her_digest, our_digest)) {
                throw new OtpAuthException("Peer authentication error.");
            }
        } catch (OtpErlangDecodeException var8) {
            throw new IOException("Handshake failed - not enough data");
        }

        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- HANDSHAKE recvChallengeReply from=" + this.peer.node + " challenge=" + challenge + " digest=" + hex(her_digest) + " local=" + this.localNode);
        }

        return challenge;
    }

    protected void sendChallengeAck(byte[] digest) throws IOException {
        OtpOutputStream obuf = new OtpOutputStream();
        obuf.write2BE(17L);
        obuf.write1(97L);
        obuf.write(digest);
        obuf.writeToAndFlush(this.socket.getOutputStream());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("-> HANDSHAKE sendChallengeAck digest=" + hex(digest) + " local=" + this.localNode);
        }

    }

    protected void recvChallengeAck(int our_challenge) throws IOException, OtpAuthException {
        byte[] her_digest = new byte[16];

        try {
            byte[] buf = this.read2BytePackage();
            OtpInputStream ibuf = new OtpInputStream(buf, 0);
            int tag = ibuf.read1();
            if (tag != 97) {
                throw new IOException("Handshake protocol error");
            }

            ibuf.readN(her_digest);
            byte[] our_digest = this.genDigest(our_challenge, this.localNode.cookie());
            if (!this.digests_equals(her_digest, our_digest)) {
                throw new OtpAuthException("Peer authentication error.");
            }
        } catch (OtpErlangDecodeException var7) {
            throw new IOException("Handshake failed - not enough data");
        } catch (Exception var8) {
            throw new OtpAuthException("Peer authentication error.");
        }

        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- HANDSHAKE recvChallengeAck from=" + this.peer.node + " digest=" + hex(her_digest) + " local=" + this.localNode);
        }

    }

    protected void sendStatus(String status) throws IOException {
        OtpOutputStream obuf = new OtpOutputStream();
        obuf.write2BE((long) (status.length() + 1));
        obuf.write1(115L);
        obuf.write(status.getBytes());
        obuf.writeToAndFlush(this.socket.getOutputStream());
        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("-> HANDSHAKE sendStatus status=" + status + " local=" + this.localNode);
        }

    }

    protected void recvStatus() throws IOException {
        try {
            byte[] buf = this.read2BytePackage();
            OtpInputStream ibuf = new OtpInputStream(buf, 0);
            int tag = ibuf.read1();
            if (tag != 115) {
                throw new IOException("Handshake protocol error");
            }

            byte[] tmpbuf = new byte[buf.length - 1];
            ibuf.readN(tmpbuf);
            String status = OtpErlangString.newString(tmpbuf);
            if (status.compareTo("ok") != 0) {
                throw new IOException("Peer replied with status '" + status + "' instead of 'ok'");
            }
        } catch (OtpErlangDecodeException var6) {
            throw new IOException("Handshake failed - not enough data");
        }

        if (this.traceLevel >= handshakeThreshold) {
            System.out.println("<- HANDSHAKE recvStatus (ok) local=" + this.localNode);
        }

    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}
