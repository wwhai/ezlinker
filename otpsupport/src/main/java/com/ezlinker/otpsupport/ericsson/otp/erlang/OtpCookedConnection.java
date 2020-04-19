
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.IOException;


public class OtpCookedConnection extends AbstractConnection {
    protected OtpNode self;
    /*  66 */   protected Links links = null;


    OtpCookedConnection(OtpNode self, OtpTransport s) throws IOException, OtpAuthException {
        /*  83 */
        super(self, s);
        /*  84 */
        this.self = self;
        /*  85 */
        this.links = new Links(25);
        /*  86 */
        start();

    }


    OtpCookedConnection(OtpNode self, OtpPeer other) throws IOException, OtpAuthException {
        /* 101 */
        super(self, other);
        /* 102 */
        this.self = self;
        /* 103 */
        this.links = new Links(25);
        /* 104 */
        start();

    }


    /* 110 */
    public void deliver(Exception e) {
        this.self.deliverError(this, e);
    }


    public void deliver(OtpMsg msg) {
        /* 121 */
        boolean delivered = this.self.deliver(msg);

        /* 123 */
        switch (msg.type()) {

            case 1:
                /* 125 */
                if (delivered) {
                    /* 126 */
                    this.links.addLink(msg.getRecipientPid(), msg.getSenderPid());

                    break;

                }

                try {
                    /* 130 */
                    sendExit(msg.getRecipientPid(), msg.getSenderPid(), new OtpErlangAtom("noproc"));

                }
                /* 132 */ catch (IOException iOException) {
                }

                break;


            case 3:

            case 4:
                /* 139 */
                this.links.removeLink(msg.getRecipientPid(), msg.getSenderPid());

                break;

        }

    }


    /* 156 */   void send(OtpErlangPid from, OtpErlangPid dest, OtpErlangObject msg) throws IOException {
        sendBuf(from, dest, new OtpOutputStream(msg));
    }


    /* 167 */   void send(OtpErlangPid from, String dest, OtpErlangObject msg) throws IOException {
        sendBuf(from, dest, new OtpOutputStream(msg));
    }


    public void close() {
        /* 172 */
        super.close();
        /* 173 */
        breakLinks();

    }


    /* 178 */
    protected void finalize() {
        close();
    }


    void exit(OtpErlangPid from, OtpErlangPid to, OtpErlangObject reason) {

        try {
            /* 187 */
            sendExit(from, to, reason);
            /* 188 */
        } catch (Exception exception) {
        }

    }


    void exit2(OtpErlangPid from, OtpErlangPid to, OtpErlangObject reason) {

        try {
            /* 198 */
            sendExit2(from, to, reason);
            /* 199 */
        } catch (Exception exception) {
        }

    }


    synchronized void link(OtpErlangPid from, OtpErlangPid to) throws OtpErlangExit {

        try {
            /* 209 */
            sendLink(from, to);
            /* 210 */
            this.links.addLink(from, to);
            /* 211 */
        } catch (IOException e) {
            /* 212 */
            throw new OtpErlangExit("noproc", to);

        }

    }


    synchronized void unlink(OtpErlangPid from, OtpErlangPid to) {
        /* 220 */
        this.links.removeLink(from, to);

        try {
            /* 222 */
            sendUnlink(from, to);
            /* 223 */
        } catch (IOException iOException) {
        }

    }


    synchronized void breakLinks() {
        /* 232 */
        if (this.links != null) {
            /* 233 */
            Link[] l = this.links.clearLinks();

            /* 235 */
            if (l != null) {
                /* 236 */
                int len = l.length;

                /* 238 */
                for (int i = 0; i < len; i++) {
                    /* 240 */
                    this.self.deliver(new OtpMsg(3, l[i].remote(), l[i]
/* 241 */.local(), new OtpErlangAtom("noconnection")));

                }

            }

        }

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpCookedConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */