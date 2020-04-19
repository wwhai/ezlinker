
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangRef
        extends OtpErlangObject {
    private static final long serialVersionUID = -7022666480768586521L;
    private final String node;
    private final int creation;
    /*  36 */   private int[] ids = null;


    @Deprecated
    public OtpErlangRef(OtpLocalNode self) {
        /*  48 */
        OtpErlangRef r = self.createRef();

        /*  50 */
        this.ids = r.ids;
        /*  51 */
        this.creation = r.creation;
        /*  52 */
        this.node = r.node;

    }


    public OtpErlangRef(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  68 */
        OtpErlangRef r = buf.read_ref();

        /*  70 */
        this.node = r.node();
        /*  71 */
        this.creation = r.creation();

        /*  73 */
        this.ids = r.ids();

    }


    public OtpErlangRef(String node, int id, int creation) {
        /*  90 */
        this.node = node;
        /*  91 */
        this.ids = new int[1];
        /*  92 */
        this.ids[0] = id & 0x3FFFF;
        /*  93 */
        this.creation = creation & 0x3;

    }


    public OtpErlangRef(String node, int[] ids, int creation) {
        /* 113 */
        this.node = node;
        /* 114 */
        this.creation = creation & 0x3;


        /* 117 */
        int len = ids.length;
        /* 118 */
        this.ids = new int[3];
        /* 119 */
        this.ids[0] = 0;
        /* 120 */
        this.ids[1] = 0;
        /* 121 */
        this.ids[2] = 0;

        /* 123 */
        if (len > 3) {
            /* 124 */
            len = 3;

        }
        /* 126 */
        System.arraycopy(ids, 0, this.ids, 0, len);
        /* 127 */
        this.ids[0] = this.ids[0] & 0x3FFFF;

    }


    /* 137 */
    public int id() {
        return this.ids[0];
    }


    /* 148 */
    public int[] ids() {
        return this.ids;
    }


    /* 157 */
    public boolean isNewRef() {
        return (this.ids.length > 1);
    }


    /* 166 */
    public int creation() {
        return this.creation;
    }


    /* 175 */
    public String node() {
        return this.node;
    }


    public String toString() {
        /* 186 */
        String s = "#Ref<" + this.node;

        /* 188 */
        for (int i = 0; i < this.ids.length; i++) {
            /* 189 */
            s = s + "." + this.ids[i];

        }

        /* 192 */
        s = s + ">";

        /* 194 */
        return s;

    }


    /* 205 */
    public void encode(OtpOutputStream buf) {
        buf.write_ref(this.node, this.ids, this.creation);
    }


    public boolean equals(Object o) {
        /* 220 */
        if (!(o instanceof OtpErlangRef)) {
            /* 221 */
            return false;

        }

        /* 224 */
        OtpErlangRef ref = (OtpErlangRef) o;

        /* 226 */
        if (!this.node.equals(ref.node()) || this.creation != ref.creation()) {
            /* 227 */
            return false;

        }

        /* 230 */
        if (isNewRef() && ref.isNewRef()) {
            /* 231 */
            return (this.ids[0] == ref.ids[0] && this.ids[1] == ref.ids[1] && this.ids[2] == ref.ids[2]);

        }

        /* 234 */
        return (this.ids[0] == ref.ids[0]);

    }


    protected int doHashCode() {
        /* 246 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(7);
        /* 247 */
        hash.combine(this.creation, this.ids[0]);
        /* 248 */
        if (isNewRef()) {
            /* 249 */
            hash.combine(this.ids[1], this.ids[2]);

        }
        /* 251 */
        return hash.valueOf();

    }


    public Object clone() {
        /* 256 */
        OtpErlangRef newRef = (OtpErlangRef) super.clone();
        /* 257 */
        newRef.ids = this.ids.clone();
        /* 258 */
        return newRef;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */