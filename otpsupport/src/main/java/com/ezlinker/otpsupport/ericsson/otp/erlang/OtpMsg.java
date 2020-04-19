
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpMsg {
    public static final int linkTag = 1;
    public static final int sendTag = 2;
    public static final int exitTag = 3;
    public static final int unlinkTag = 4;
    public static final int regSendTag = 6;
    public static final int exit2Tag = 8;
    protected int tag;
    protected OtpInputStream paybuf;
    protected OtpErlangObject payload;
    protected OtpErlangPid from;
    protected OtpErlangPid to;
    protected String toName;


    OtpMsg(OtpErlangPid to, OtpInputStream paybuf) {
        /*  74 */
        this.tag = 2;
        /*  75 */
        this.from = null;
        /*  76 */
        this.to = to;
        /*  77 */
        this.toName = null;
        /*  78 */
        this.paybuf = paybuf;
        /*  79 */
        this.payload = null;

    }


    OtpMsg(OtpErlangPid to, OtpErlangObject payload) {
        /*  84 */
        this.tag = 2;
        /*  85 */
        this.from = null;
        /*  86 */
        this.to = to;
        /*  87 */
        this.toName = null;
        /*  88 */
        this.paybuf = null;
        /*  89 */
        this.payload = payload;

    }


    OtpMsg(OtpErlangPid from, String toName, OtpInputStream paybuf) {
        /*  95 */
        this.tag = 6;
        /*  96 */
        this.from = from;
        /*  97 */
        this.toName = toName;
        /*  98 */
        this.to = null;
        /*  99 */
        this.paybuf = paybuf;
        /* 100 */
        this.payload = null;

    }


    OtpMsg(OtpErlangPid from, String toName, OtpErlangObject payload) {
        /* 106 */
        this.tag = 6;
        /* 107 */
        this.from = from;
        /* 108 */
        this.toName = toName;
        /* 109 */
        this.to = null;
        /* 110 */
        this.paybuf = null;
        /* 111 */
        this.payload = payload;

    }


    OtpMsg(int tag, OtpErlangPid from, OtpErlangPid to, OtpErlangObject reason) {
        /* 117 */
        this.tag = tag;
        /* 118 */
        this.from = from;
        /* 119 */
        this.to = to;
        /* 120 */
        this.paybuf = null;
        /* 121 */
        this.payload = reason;

    }


    OtpMsg(int tag, OtpErlangPid from, OtpErlangPid to, String reason) {
        /* 127 */
        this.tag = tag;
        /* 128 */
        this.from = from;
        /* 129 */
        this.to = to;
        /* 130 */
        this.paybuf = null;
        /* 131 */
        this.payload = new OtpErlangAtom(reason);

    }


    OtpMsg(int tag, OtpErlangPid from, OtpErlangPid to) {
        /* 137 */
        int atag = tag;
        /* 138 */
        if (tag > 10) {
            /* 139 */
            atag -= 10;

        }

        /* 142 */
        this.tag = atag;
        /* 143 */
        this.from = from;
        /* 144 */
        this.to = to;

    }


    /* 154 */   OtpInputStream getMsgBuf() {
        return this.paybuf;
    }


    /* 193 */
    public int type() {
        return this.tag;
    }


    public OtpErlangObject getMsg() throws OtpErlangDecodeException {
        /* 216 */
        if (this.payload == null) {
            /* 217 */
            this.payload = this.paybuf.read_any();

        }
        /* 219 */
        return this.payload;

    }


    /* 236 */
    public String getRecipientName() {
        return this.toName;
    }


    /* 255 */
    public OtpErlangPid getRecipientPid() {
        return this.to;
    }


    public Object getRecipient() {
        /* 273 */
        if (this.toName != null) {
            /* 274 */
            return this.toName;

        }
        /* 276 */
        return this.to;

    }


    /* 293 */
    public OtpErlangPid getSenderPid() {
        return this.from;
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpMsg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */