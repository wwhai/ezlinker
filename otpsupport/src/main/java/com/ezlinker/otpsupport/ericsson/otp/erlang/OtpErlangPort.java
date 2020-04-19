
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangPort
        extends OtpErlangObject {
    private static final long serialVersionUID = 4037115468007644704L;
    private final String node;
    private final int id;
    private final int creation;


    private OtpErlangPort(OtpSelf self) {
        /*  43 */
        OtpErlangPort p = self.createPort();

        /*  45 */
        this.id = p.id;
        /*  46 */
        this.creation = p.creation;
        /*  47 */
        this.node = p.node;

    }


    public OtpErlangPort(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  63 */
        OtpErlangPort p = buf.read_port();

        /*  65 */
        this.node = p.node();
        /*  66 */
        this.id = p.id();
        /*  67 */
        this.creation = p.creation();

    }


    public OtpErlangPort(String node, int id, int creation) {
        /*  84 */
        this.node = node;
        /*  85 */
        this.id = id & 0xFFFFFFF;
        /*  86 */
        this.creation = creation & 0x3;

    }


    /*  95 */
    public int id() {
        return this.id;
    }


    /* 104 */
    public int creation() {
        return this.creation;
    }


    /* 113 */
    public String node() {
        return this.node;
    }


    /* 124 */
    public String toString() {
        return "#Port<" + this.node + "." + this.id + ">";
    }


    /* 135 */
    public void encode(OtpOutputStream buf) {
        buf.write_port(this.node, this.id, this.creation);
    }


    public boolean equals(Object o) {
        /* 149 */
        if (!(o instanceof OtpErlangPort)) {
            /* 150 */
            return false;

        }

        /* 153 */
        OtpErlangPort port = (OtpErlangPort) o;

        /* 155 */
        return (this.creation == port.creation && this.id == port.id && this.node
/* 156 */.compareTo(port.node) == 0);

    }


    protected int doHashCode() {
        /* 161 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(6);
        /* 162 */
        hash.combine(this.creation);
        /* 163 */
        hash.combine(this.id, this.node.hashCode());
        /* 164 */
        return hash.valueOf();

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangPort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */