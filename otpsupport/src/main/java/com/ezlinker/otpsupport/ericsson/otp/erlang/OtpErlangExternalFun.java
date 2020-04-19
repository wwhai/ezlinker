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
/*    */ public class OtpErlangExternalFun
        /*    */ extends OtpErlangObject
        /*    */ {
    /*    */   private static final long serialVersionUID = 6443965570641913886L;
    /*    */   private final String module;
    /*    */   private final String function;
    /*    */   private final int arity;

    /*    */
    /*    */
    public OtpErlangExternalFun(String module, String function, int arity) {
        /* 33 */
        this.module = module;
        /* 34 */
        this.function = function;
        /* 35 */
        this.arity = arity;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public OtpErlangExternalFun(OtpInputStream buf) throws OtpErlangDecodeException {
        /* 40 */
        OtpErlangExternalFun f = buf.read_external_fun();
        /* 41 */
        this.module = f.module;
        /* 42 */
        this.function = f.function;
        /* 43 */
        this.arity = f.arity;
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /* 48 */
    public void encode(OtpOutputStream buf) {
        buf.write_external_fun(this.module, this.function, this.arity);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    public boolean equals(Object o) {
        /* 53 */
        if (!(o instanceof OtpErlangExternalFun)) {
            /* 54 */
            return false;
            /*    */
        }
        /* 56 */
        OtpErlangExternalFun f = (OtpErlangExternalFun) o;
        /* 57 */
        return (this.module.equals(f.module) && this.function.equals(f.function) && this.arity == f.arity);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    protected int doHashCode() {
        /* 63 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(14);
        /* 64 */
        hash.combine(this.module.hashCode(), this.function.hashCode());
        /* 65 */
        hash.combine(this.arity);
        /* 66 */
        return hash.valueOf();
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /* 71 */
    public String toString() {
        return "#Fun<" + this.module + "." + this.function + "." + this.arity + ">";
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangExternalFun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */