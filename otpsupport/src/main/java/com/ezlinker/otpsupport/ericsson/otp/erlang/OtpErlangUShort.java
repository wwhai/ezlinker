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
/*    */
/*    */ public class OtpErlangUShort
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   static final long serialVersionUID = 300370950578307246L;

    /*    */
    /*    */
    public OtpErlangUShort(short s) throws OtpErlangRangeException {
        /* 39 */
        super(s);
        /*    */
        /* 41 */
        uShortValue();
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
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    public OtpErlangUShort(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 61 */
        super(buf);
        /*    */
        /* 63 */
        uShortValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangUShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */