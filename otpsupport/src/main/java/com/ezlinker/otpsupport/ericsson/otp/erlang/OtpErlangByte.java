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
/*    */ public class OtpErlangByte
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   private static final long serialVersionUID = 5778019796466613446L;

    /*    */
    /* 36 */
    public OtpErlangByte(byte b) {
        super(b);
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
    public OtpErlangByte(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 55 */
        super(buf);
        /*    */
        /* 57 */
        byteValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */