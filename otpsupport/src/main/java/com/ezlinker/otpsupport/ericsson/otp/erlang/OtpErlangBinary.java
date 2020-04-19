/*    */
package com.ezlinker.otpsupport.ericsson.otp.erlang;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class OtpErlangBinary
        /*    */ extends OtpErlangBitstr
        /*    */ {
    /*    */   private static final long serialVersionUID = -3781009633593609217L;

    /*    */
    /* 37 */
    public OtpErlangBinary(byte[] bin) {
        super(bin);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    public OtpErlangBinary(OtpInputStream buf) throws OtpErlangDecodeException {
        /* 53 */
        super(new byte[0]);
        /* 54 */
        this.bin = buf.read_binary();
        /* 55 */
        this.pad_bits = 0;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 66 */
    public OtpErlangBinary(Object o) {
        super(o);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 78 */
    public void encode(OtpOutputStream buf) {
        buf.write_binary(this.bin);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    public Object clone() {
        /* 83 */
        OtpErlangBinary that = (OtpErlangBinary) super.clone();
        /* 84 */
        return that;
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangBinary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */