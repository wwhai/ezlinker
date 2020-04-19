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
/*    */ public class OtpErlangInt
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   private static final long serialVersionUID = 1229430977614805556L;

    /*    */
    /* 36 */
    public OtpErlangInt(int i) {
        super(i);
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
    public OtpErlangInt(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 55 */
        super(buf);
        /*    */
        /* 57 */
        intValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */