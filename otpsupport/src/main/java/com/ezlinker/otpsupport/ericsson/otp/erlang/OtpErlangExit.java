
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangExit
        extends OtpErlangException {
    private static final long serialVersionUID = 1L;
    /*  38 */ OtpErlangObject reason = null;
    /*  39 */ OtpErlangPid pid = null;


    public OtpErlangExit(OtpErlangObject reason) {
        /*  48 */
        super(reason.toString());
        /*  49 */
        this.reason = reason;

    }


    /*  64 */
    public OtpErlangExit(String reason) {
        this(new OtpErlangAtom(reason));
    }


    public OtpErlangExit(OtpErlangObject reason, OtpErlangPid pid) {
        /*  77 */
        super(reason.toString());
        /*  78 */
        this.reason = reason;
        /*  79 */
        this.pid = pid;

    }


    /*  97 */
    public OtpErlangExit(String reason, OtpErlangPid pid) {
        this(new OtpErlangAtom(reason), pid);
    }


    /* 104 */
    public OtpErlangObject reason() {
        return this.reason;
    }


    /* 111 */
    public OtpErlangPid pid() {
        return this.pid;
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangExit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */