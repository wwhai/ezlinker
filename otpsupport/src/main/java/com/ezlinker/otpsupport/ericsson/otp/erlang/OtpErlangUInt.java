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
/*    */ public class OtpErlangUInt
        /*    */ extends OtpErlangLong
        /*    */ {
    /*    */   static final long serialVersionUID = -1450956122937471885L;

    /*    */
    /*    */
    public OtpErlangUInt(int i) throws OtpErlangRangeException {
        /* 39 */
        super(i);
        /*    */
        /* 41 */
        uIntValue();
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
    public OtpErlangUInt(OtpInputStream buf) throws OtpErlangRangeException, OtpErlangDecodeException {
        /* 61 */
        super(buf);
        /*    */
        /* 63 */
        uIntValue();
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangUInt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */