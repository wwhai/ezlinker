
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.IOException;


public class OtpConnection
        extends AbstractConnection {
    protected OtpSelf self;
    protected GenericQueue queue;


    OtpConnection(OtpSelf self, OtpTransport s) throws IOException, OtpAuthException {
        /*  68 */
        super(self, s);
        /*  69 */
        this.self = self;
        /*  70 */
        this.queue = new GenericQueue();
        /*  71 */
        start();

    }


    OtpConnection(OtpSelf self, OtpPeer other) throws IOException, OtpAuthException {
        /*  86 */
        super(self, other);
        /*  87 */
        this.self = self;
        /*  88 */
        this.queue = new GenericQueue();
        /*  89 */
        start();

    }


    /*  94 */
    public void deliver(Exception e) {
        this.queue.put(e);
    }


    /*  99 */
    public void deliver(OtpMsg msg) {
        this.queue.put(msg);
    }


    /* 108 */
    public OtpPeer peer() {
        return this.peer;
    }


    /* 117 */
    public OtpSelf self() {
        return this.self;
    }


    /* 125 */
    public int msgCount() {
        return this.queue.getCount();
    }


    public OtpErlangObject receive() throws IOException, OtpErlangExit, OtpAuthException {

        try {
            /* 153 */
            return receiveMsg().getMsg();
            /* 154 */
        } catch (OtpErlangDecodeException e) {
            /* 155 */
            close();
            /* 156 */
            throw new IOException(e.getMessage());

        }

    }


    public OtpErlangObject receive(long timeout) throws InterruptedException, IOException, OtpErlangExit, OtpAuthException {

        try {
            /* 195 */
            return receiveMsg(timeout).getMsg();
            /* 196 */
        } catch (OtpErlangDecodeException e) {
            /* 197 */
            close();
            /* 198 */
            throw new IOException(e.getMessage());

        }

    }


    /* 226 */
    public OtpInputStream receiveBuf() throws IOException, OtpErlangExit, OtpAuthException {
        return receiveMsg().getMsgBuf();
    }


    /* 263 */
    public OtpInputStream receiveBuf(long timeout) throws InterruptedException, IOException, OtpErlangExit, OtpAuthException {
        return receiveMsg(timeout).getMsgBuf();
    }


    public OtpMsg receiveMsg() throws IOException, OtpErlangExit, OtpAuthException {
        /* 286 */
        Object o = this.queue.get();

        /* 288 */
        if (o instanceof OtpMsg)
            /* 289 */ return (OtpMsg) o;
        /* 290 */
        if (o instanceof IOException)
            /* 291 */ throw (IOException) o;
        /* 292 */
        if (o instanceof OtpErlangExit)
            /* 293 */ throw (OtpErlangExit) o;
        /* 294 */
        if (o instanceof OtpAuthException) {
            /* 295 */
            throw (OtpAuthException) o;

        }

        /* 298 */
        return null;

    }


    public OtpMsg receiveMsg(long timeout) throws InterruptedException, IOException, OtpErlangExit, OtpAuthException {
        /* 330 */
        Object o = this.queue.get(timeout);

        /* 332 */
        if (o instanceof OtpMsg)
            /* 333 */ return (OtpMsg) o;
        /* 334 */
        if (o instanceof IOException)
            /* 335 */ throw (IOException) o;
        /* 336 */
        if (o instanceof OtpErlangExit)
            /* 337 */ throw (OtpErlangExit) o;
        /* 338 */
        if (o instanceof OtpAuthException) {
            /* 339 */
            throw (OtpAuthException) o;

        }

        /* 342 */
        return null;

    }


    /* 361 */
    public void send(OtpErlangPid dest, OtpErlangObject msg) throws IOException {
        sendBuf(this.self.pid(), dest, new OtpOutputStream(msg));
    }


    /* 380 */
    public void send(String dest, OtpErlangObject msg) throws IOException {
        sendBuf(this.self.pid(), dest, new OtpOutputStream(msg));
    }


    /* 397 */
    public void sendBuf(String dest, OtpOutputStream payload) throws IOException {
        sendBuf(this.self.pid(), dest, payload);
    }


    /* 414 */
    public void sendBuf(OtpErlangPid dest, OtpOutputStream payload) throws IOException {
        sendBuf(this.self.pid(), dest, payload);
    }


    /* 445 */
    public void sendRPC(String mod, String fun, OtpErlangObject[] args) throws IOException {
        sendRPC(mod, fun, new OtpErlangList(args));
    }


    public void sendRPC(String mod, String fun, OtpErlangList args) throws IOException {
        /* 476 */
        OtpErlangObject[] rpc = new OtpErlangObject[2];
        /* 477 */
        OtpErlangObject[] call = new OtpErlangObject[5];



        /* 481 */
        call[0] = new OtpErlangAtom("call");
        /* 482 */
        call[1] = new OtpErlangAtom(mod);
        /* 483 */
        call[2] = new OtpErlangAtom(fun);
        /* 484 */
        call[3] = args;
        /* 485 */
        call[4] = new OtpErlangAtom("user");

        /* 487 */
        rpc[0] = this.self.pid();
        /* 488 */
        rpc[1] = new OtpErlangTuple(call);

        /* 490 */
        send("rex", new OtpErlangTuple(rpc));

    }


    public OtpErlangObject receiveRPC() throws IOException, OtpErlangExit, OtpAuthException {
        /* 521 */
        OtpErlangObject msg = receive();

        /* 523 */
        if (msg instanceof OtpErlangTuple) {
            /* 524 */
            OtpErlangTuple t = (OtpErlangTuple) msg;
            /* 525 */
            if (t.arity() == 2) {
                /* 526 */
                return t.elementAt(1);

            }

        }

        /* 530 */
        return null;

    }


    /* 547 */
    public void link(OtpErlangPid dest) throws IOException {
        sendLink(this.self.pid(), dest);
    }


    /* 563 */
    public void unlink(OtpErlangPid dest) throws IOException {
        sendUnlink(this.self.pid(), dest);
    }


    /* 580 */
    public void exit(OtpErlangPid dest, OtpErlangObject reason) throws IOException {
        sendExit2(this.self.pid(), dest, reason);
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */