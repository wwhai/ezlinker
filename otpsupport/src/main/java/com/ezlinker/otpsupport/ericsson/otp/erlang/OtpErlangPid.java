
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangPid
        extends OtpErlangObject
        implements Comparable<Object> {
    private static final long serialVersionUID = 1664394142301803659L;
    private final String node;
    private final int id;
    private final int serial;
    private final int creation;


    @Deprecated
    public OtpErlangPid(OtpLocalNode self) {
        /*  45 */
        OtpErlangPid p = self.createPid();

        /*  47 */
        this.id = p.id;
        /*  48 */
        this.serial = p.serial;
        /*  49 */
        this.creation = p.creation;
        /*  50 */
        this.node = p.node;

    }


    public OtpErlangPid(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  66 */
        OtpErlangPid p = buf.read_pid();

        /*  68 */
        this.node = p.node();
        /*  69 */
        this.id = p.id();
        /*  70 */
        this.serial = p.serial();
        /*  71 */
        this.creation = p.creation();

    }


    public OtpErlangPid(String node, int id, int serial, int creation) {
        /*  93 */
        this.node = node;
        /*  94 */
        this.id = id & 0x7FFF;
        /*  95 */
        this.serial = serial & 0x1FFF;
        /*  96 */
        this.creation = creation & 0x3;

    }


    /* 105 */
    public int serial() {
        return this.serial;
    }


    /* 114 */
    public int id() {
        return this.id;
    }


    /* 123 */
    public int creation() {
        return this.creation;
    }


    /* 132 */
    public String node() {
        return this.node;
    }


    /* 143 */
    public String toString() {
        return "#Pid<" + this.node + "." + this.id + "." + this.serial + ">";
    }


    /* 154 */
    public void encode(OtpOutputStream buf) {
        buf.write_pid(this.node, this.id, this.serial, this.creation);
    }


    public boolean equals(Object o) {
        /* 168 */
        if (!(o instanceof OtpErlangPid)) {
            /* 169 */
            return false;

        }

        /* 172 */
        OtpErlangPid pid = (OtpErlangPid) o;

        /* 174 */
        return (this.creation == pid.creation && this.serial == pid.serial && this.id == pid.id && this.node
/* 175 */.compareTo(pid.node) == 0);

    }


    protected int doHashCode() {
        /* 180 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(5);
        /* 181 */
        hash.combine(this.creation, this.serial);
        /* 182 */
        hash.combine(this.id, this.node.hashCode());
        /* 183 */
        return hash.valueOf();

    }


    public int compareTo(Object o) {
        /* 187 */
        if (!(o instanceof OtpErlangPid)) {
            /* 188 */
            return -1;

        }

        /* 191 */
        OtpErlangPid pid = (OtpErlangPid) o;
        /* 192 */
        if (this.creation == pid.creation) {
            /* 193 */
            if (this.serial == pid.serial) {
                /* 194 */
                if (this.id == pid.id) {
                    /* 195 */
                    return this.node.compareTo(pid.node);

                }
                /* 197 */
                return this.id - pid.id;

            }
            /* 199 */
            return this.serial - pid.serial;

        }
        /* 201 */
        return this.creation - pid.creation;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangPid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */