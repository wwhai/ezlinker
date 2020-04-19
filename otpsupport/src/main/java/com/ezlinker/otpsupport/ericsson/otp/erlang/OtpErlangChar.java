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
/*    */ public class OtpErlangChar
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   private static final long serialVersionUID = 3225337815669398204L;

    /*    */
    /* 36 */
    public OtpErlangChar(char c) {
        super(c);
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
    public OtpErlangChar(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 55 */
        super(buf);
        /*    */
        /* 57 */
        charValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangChar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */