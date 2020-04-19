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
/*    */ class Link
        /*    */ {
    /*    */   private final OtpErlangPid local;
    /*    */   private final OtpErlangPid remote;
    /* 26 */   private int hashCodeValue = 0;

    /*    */
    /*    */
    public Link(OtpErlangPid local, OtpErlangPid remote) {
        /* 29 */
        this.local = local;
        /* 30 */
        this.remote = remote;
        /*    */
    }

    /*    */
    /*    */
    /* 34 */
    public OtpErlangPid local() {
        return this.local;
    }

    /*    */
    /*    */
    /*    */
    /* 38 */
    public OtpErlangPid remote() {
        return this.remote;
    }

    /*    */
    /*    */
    /*    */
    /* 42 */
    public boolean contains(OtpErlangPid pid) {
        return (this.local.equals(pid) || this.remote.equals(pid));
    }

    /*    */
    /*    */
    /*    */
    public boolean equals(OtpErlangPid alocal, OtpErlangPid aremote) {
        /* 46 */
        return ((this.local.equals(alocal) && this.remote.equals(aremote)) || (this.local
/* 47 */.equals(aremote) && this.remote.equals(alocal)));
        /*    */
    }

    /*    */
    /*    */
    /*    */
    public int hashCode() {
        /* 52 */
        if (this.hashCodeValue == 0) {
            /* 53 */
            OtpErlangObject.Hash hash = new OtpErlangObject.Hash(5);
            /* 54 */
            hash.combine(this.local.hashCode() + this.remote.hashCode());
            /* 55 */
            this.hashCodeValue = hash.valueOf();
            /*    */
        }
        /* 57 */
        return this.hashCodeValue;
        /*    */
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\Link.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */