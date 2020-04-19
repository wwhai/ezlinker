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
/*    */ public class OtpErlangFloat
        /*    */ extends OtpErlangDouble
        /*    */ {
    /*    */   private static final long serialVersionUID = -2231546377289456934L;

    /*    */
    /* 33 */
    public OtpErlangFloat(float f) {
        super(f);
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
    public OtpErlangFloat(OtpInputStream buf) throws OtpErlangDecodeException, OtpErlangRangeException {
        /* 52 */
        super(buf);
        /*    */
        /* 54 */
        floatValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */