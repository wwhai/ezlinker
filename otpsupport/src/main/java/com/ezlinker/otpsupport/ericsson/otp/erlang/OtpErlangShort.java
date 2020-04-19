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
/*    */ public class OtpErlangShort
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   static final long serialVersionUID = 7162345156603088099L;

    /*    */
    /* 36 */
    public OtpErlangShort(short s) {
        super(s);
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
    public OtpErlangShort(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 55 */
        super(buf);
        /*    */
        /* 57 */
        shortValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */