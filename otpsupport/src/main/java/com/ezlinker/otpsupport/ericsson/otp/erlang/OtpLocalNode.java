
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpLocalNode
        extends AbstractNode {

    protected int port;

    protected OtpTransport epmd;
    /*  27 */   private int serial = 0;
    /*  28 */   private int pidCount = 1;
    /*  29 */   private int portCount = 1;

    private int[] refId;


    protected OtpLocalNode(String node) {
        /*  39 */
        super(node);
        /*  40 */
        init();

    }


    protected OtpLocalNode(String node, OtpTransportFactory transportFactory) {
        /*  49 */
        super(node, transportFactory);
        /*  50 */
        init();

    }


    protected OtpLocalNode(String node, String cookie) {
        /*  57 */
        super(node, cookie);
        /*  58 */
        init();

    }


    protected OtpLocalNode(String node, String cookie, OtpTransportFactory transportFactory) {
        /*  66 */
        super(node, cookie, transportFactory);
        /*  67 */
        init();

    }


    private void init() {
        /*  71 */
        this.serial = 0;
        /*  72 */
        this.pidCount = 1;
        /*  73 */
        this.portCount = 1;
        /*  74 */
        this.refId = new int[3];
        /*  75 */
        this.refId[0] = 1;
        /*  76 */
        this.refId[1] = 0;
        /*  77 */
        this.refId[2] = 0;

    }


    /*  86 */
    public int port() {
        return this.port;
    }


    /* 105 */
    protected OtpTransport getEpmd() {
        return this.epmd;
    }


    /*  96 */
    protected void setEpmd(OtpTransport s) {
        this.epmd = s;
    }


    public synchronized OtpErlangPid createPid() {
        /* 116 */
        OtpErlangPid p = new OtpErlangPid(this.node, this.pidCount, this.serial, this.creation);


        /* 119 */
        this.pidCount++;
        /* 120 */
        if (this.pidCount > 32767) {
            /* 121 */
            this.pidCount = 0;

            /* 123 */
            this.serial++;
            /* 124 */
            if (this.serial > 8191) {
                /* 125 */
                this.serial = 0;

            }

        }

        /* 129 */
        return p;

    }


    public synchronized OtpErlangPort createPort() {
        /* 142 */
        OtpErlangPort p = new OtpErlangPort(this.node, this.portCount, this.creation);

        /* 144 */
        this.portCount++;
        /* 145 */
        if (this.portCount > 268435455) {
            /* 146 */
            this.portCount = 0;

        }

        /* 149 */
        return p;

    }


    public synchronized OtpErlangRef createRef() {
        /* 161 */
        OtpErlangRef r = new OtpErlangRef(this.node, this.refId, this.creation);


        /* 164 */
        this.refId[0] = this.refId[0] + 1;
        /* 165 */
        if (this.refId[0] > 262143) {
            /* 166 */
            this.refId[0] = 0;

            /* 168 */
            this.refId[1] = this.refId[1] + 1;
            /* 169 */
            if (this.refId[1] == 0) {
                /* 170 */
                this.refId[2] = this.refId[2] + 1;

            }

        }

        /* 174 */
        return r;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpLocalNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */