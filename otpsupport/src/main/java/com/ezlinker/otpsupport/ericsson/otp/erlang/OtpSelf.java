
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.IOException;


public class OtpSelf
        extends OtpLocalNode {
    private final OtpServerTransport sock;
    private final OtpErlangPid pid;


    /*  74 */
    public OtpSelf(String node) throws IOException {
        this(node, defaultCookie, 0);
    }


    /* 102 */
    public OtpSelf(String node, OtpTransportFactory transportFactory) throws IOException {
        this(node, defaultCookie, 0, transportFactory);
    }


    /* 119 */
    public OtpSelf(String node, String cookie) throws IOException {
        this(node, cookie, 0);
    }


    /* 140 */
    public OtpSelf(String node, String cookie, OtpTransportFactory transportFactory) throws IOException {
        this(node, cookie, 0, transportFactory);
    }


    public OtpSelf(String node, String cookie, int port) throws IOException {
        /* 162 */
        super(node, cookie);

        /* 164 */
        this.sock = createServerTransport(port);

        /* 166 */
        if (port != 0) {
            /* 167 */
            this.port = port;

        } else {
            /* 169 */
            this.port = this.sock.getLocalPort();

        }

        /* 172 */
        this.pid = createPid();

    }


    public OtpSelf(String node, String cookie, int port, OtpTransportFactory transportFactory) throws IOException {
        /* 197 */
        super(node, cookie, transportFactory);

        /* 199 */
        this.sock = createServerTransport(port);

        /* 201 */
        if (port != 0) {
            /* 202 */
            this.port = port;

        } else {
            /* 204 */
            this.port = this.sock.getLocalPort();

        }

        /* 207 */
        this.pid = createPid();

    }


    /* 220 */
    public OtpErlangPid pid() {
        return this.pid;
    }


    public boolean publishPort() throws IOException {
        /* 247 */
        if (getEpmd() != null) {
            /* 248 */
            return false;

        }

        /* 251 */
        OtpEpmd.publishPort(this);
        /* 252 */
        return (getEpmd() != null);

    }


    public void unPublishPort() {
        /* 261 */
        OtpEpmd.unPublishPort(this);


        try {
            /* 265 */
            if (this.epmd != null) {
                /* 266 */
                this.epmd.close();

            }
            /* 268 */
        } catch (IOException iOException) {
        }

        /* 270 */
        this.epmd = null;

    }


    public OtpConnection accept() throws IOException, OtpAuthException {
        /* 288 */
        OtpTransport newsock = null;


        try {
            /* 292 */
            newsock = this.sock.accept();
            /* 293 */
            return new OtpConnection(this, newsock);
            /* 294 */
        } catch (IOException e) {

            try {
                /* 296 */
                if (newsock != null) {
                    /* 297 */
                    newsock.close();

                }
                /* 299 */
            } catch (IOException iOException) {
            }

            /* 301 */
            throw e;

        }

    }


    /* 325 */
    public OtpConnection connect(OtpPeer other) throws IOException, OtpAuthException {
        return new OtpConnection(this, other);
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpSelf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */