
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.*;


public class OtpErlangBitstr
        extends OtpErlangObject {
    private static final long serialVersionUID = -3781009633593609217L;
    protected byte[] bin;
    protected int pad_bits;


    public OtpErlangBitstr(byte[] bin) {
        /*  44 */
        this.bin = new byte[bin.length];
        /*  45 */
        System.arraycopy(bin, 0, this.bin, 0, bin.length);
        /*  46 */
        this.pad_bits = 0;

    }


    public OtpErlangBitstr(byte[] bin, int pad_bits) {
        /*  58 */
        this.bin = new byte[bin.length];
        /*  59 */
        System.arraycopy(bin, 0, this.bin, 0, bin.length);
        /*  60 */
        this.pad_bits = pad_bits;

        /*  62 */
        check_bitstr(this.bin, this.pad_bits);

    }


    public OtpErlangBitstr(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  93 */
        int[] pbs = {0};

        /*  95 */
        this.bin = buf.read_bitstr(pbs);
        /*  96 */
        this.pad_bits = pbs[0];

        /*  98 */
        check_bitstr(this.bin, this.pad_bits);

    }


    public OtpErlangBitstr(Object o) {

        try {
            /* 110 */
            this.bin = toByteArray(o);
            /* 111 */
            this.pad_bits = 0;
            /* 112 */
        } catch (IOException e) {
            /* 113 */
            throw new IllegalArgumentException("Object must implement Serializable");

        }

    }


    private static byte[] toByteArray(Object o) throws IOException {
        /* 121 */
        if (o == null) {
            /* 122 */
            return null;

        }


        /* 126 */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /* 127 */
        ObjectOutputStream oos = new ObjectOutputStream(baos);


        /* 130 */
        oos.writeObject(o);
        /* 131 */
        oos.flush();

        /* 133 */
        return baos.toByteArray();

    }


    private static Object fromByteArray(byte[] buf) {
        /* 137 */
        if (buf == null) {
            /* 138 */
            return null;

        }


        /* 142 */
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);

            /* 144 */
            ObjectInputStream ois = new ObjectInputStream(bais);

            /* 146 */
            return ois.readObject();
        }
        /* 147 */ catch (ClassNotFoundException classNotFoundException) {
        }
        /* 148 */ catch (IOException iOException) {
        }


        /* 151 */
        return null;

    }


    private void check_bitstr(byte[] abin, int a_pad_bits) {
        /*  66 */
        if (a_pad_bits < 0 || 7 < a_pad_bits) {
            /*  67 */
            throw new IllegalArgumentException("Padding must be in range 0..7");

        }

        /*  70 */
        if (a_pad_bits != 0 && abin.length == 0) {
            /*  71 */
            throw new IllegalArgumentException("Padding on zero length bitstr");

        }

        /*  74 */
        if (abin.length != 0) {
            /*  76 */
            abin[abin.length - 1] = (byte) (abin[abin.length - 1] & ((1 << a_pad_bits) - 1 ^ 0xFFFFFFFF));

        }

    }


    /* 161 */
    public byte[] binaryValue() {
        return this.bin;
    }


    public int size() {
        /* 171 */
        if (this.pad_bits == 0) {
            /* 172 */
            return this.bin.length;

        }
        /* 174 */
        if (this.bin.length == 0) {
            /* 175 */
            throw new IllegalStateException("Impossible length");

        }
        /* 177 */
        return this.bin.length - 1;

    }


    /* 187 */
    public int pad_bits() {
        return this.pad_bits;
    }


    public Object getObject() {
        /* 199 */
        if (this.pad_bits != 0) {
            /* 200 */
            return null;

        }
        /* 202 */
        return fromByteArray(this.bin);

    }


    public String toString() {
        /* 214 */
        if (this.pad_bits == 0) {
            /* 215 */
            return "#Bin<" + this.bin.length + ">";

        }
        /* 217 */
        if (this.bin.length == 0) {
            /* 218 */
            throw new IllegalStateException("Impossible length");

        }
        /* 220 */
        return "#Bin<" + this.bin.length + "-" + this.pad_bits + ">";

    }


    /* 232 */
    public void encode(OtpOutputStream buf) {
        buf.write_bitstr(this.bin, this.pad_bits);
    }


    public boolean equals(Object o) {
        /* 246 */
        if (!(o instanceof OtpErlangBitstr)) {
            /* 247 */
            return false;

        }

        /* 250 */
        OtpErlangBitstr that = (OtpErlangBitstr) o;
        /* 251 */
        if (this.pad_bits != that.pad_bits) {
            /* 252 */
            return false;

        }

        /* 255 */
        int len = this.bin.length;
        /* 256 */
        if (len != that.bin.length) {
            /* 257 */
            return false;

        }

        /* 260 */
        for (int i = 0; i < len; i++) {
            /* 261 */
            if (this.bin[i] != that.bin[i]) {
                /* 262 */
                return false;

            }

        }

        /* 266 */
        return true;

    }


    protected int doHashCode() {
        /* 271 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(15);
        /* 272 */
        hash.combine(this.bin);
        /* 273 */
        hash.combine(this.pad_bits);
        /* 274 */
        return hash.valueOf();

    }


    public Object clone() {
        /* 279 */
        OtpErlangBitstr that = (OtpErlangBitstr) super.clone();
        /* 280 */
        that.bin = this.bin.clone();
        /* 281 */
        that.pad_bits = this.pad_bits;
        /* 282 */
        return that;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangBitstr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */