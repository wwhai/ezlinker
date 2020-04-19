
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.util.Arrays;


public class OtpErlangFun
        extends OtpErlangObject {
    private static final long serialVersionUID = -3423031125356706472L;
    private final OtpErlangPid pid;
    private final String module;
    private final long index;
    private final long old_index;
    private final long uniq;
    private final OtpErlangObject[] freeVars;
    private final int arity;
    private final byte[] md5;


    public OtpErlangFun(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  39 */
        OtpErlangFun f = buf.read_fun();
        /*  40 */
        this.pid = f.pid;
        /*  41 */
        this.module = f.module;
        /*  42 */
        this.arity = f.arity;
        /*  43 */
        this.md5 = f.md5;
        /*  44 */
        this.index = f.index;
        /*  45 */
        this.old_index = f.old_index;
        /*  46 */
        this.uniq = f.uniq;
        /*  47 */
        this.freeVars = f.freeVars;

    }


    public OtpErlangFun(OtpErlangPid pid, String module, long index, long uniq, OtpErlangObject[] freeVars) {
        /*  52 */
        this.pid = pid;
        /*  53 */
        this.module = module;
        /*  54 */
        this.arity = -1;
        /*  55 */
        this.md5 = null;
        /*  56 */
        this.index = index;
        /*  57 */
        this.old_index = 0L;
        /*  58 */
        this.uniq = uniq;
        /*  59 */
        this.freeVars = freeVars;

    }


    public OtpErlangFun(OtpErlangPid pid, String module, int arity, byte[] md5, int index, long old_index, long uniq, OtpErlangObject[] freeVars) {
        /*  66 */
        this.pid = pid;
        /*  67 */
        this.module = module;
        /*  68 */
        this.arity = arity;
        /*  69 */
        this.md5 = md5;
        /*  70 */
        this.index = index;
        /*  71 */
        this.old_index = old_index;
        /*  72 */
        this.uniq = uniq;
        /*  73 */
        this.freeVars = freeVars;

    }


    /*  78 */
    public void encode(OtpOutputStream buf) {
        buf.write_fun(this.pid, this.module, this.old_index, this.arity, this.md5, this.index, this.uniq, this.freeVars);
    }


    public boolean equals(Object o) {
        /*  83 */
        if (!(o instanceof OtpErlangFun)) {
            /*  84 */
            return false;

        }
        /*  86 */
        OtpErlangFun f = (OtpErlangFun) o;
        /*  87 */
        if (!this.pid.equals(f.pid) || !this.module.equals(f.module) || this.arity != f.arity) {
            /*  88 */
            return false;

        }
        /*  90 */
        if (this.md5 == null) {
            /*  91 */
            if (f.md5 != null) {
                /*  92 */
                return false;

            }

        }
        /*  95 */
        else if (!Arrays.equals(this.md5, f.md5)) {
            /*  96 */
            return false;

        }

        /*  99 */
        if (this.index != f.index || this.uniq != f.uniq) {
            /* 100 */
            return false;

        }
        /* 102 */
        if (this.freeVars == null) {
            /* 103 */
            return (f.freeVars == null);

        }
        /* 105 */
        return Arrays.equals(this.freeVars, f.freeVars);

    }


    protected int doHashCode() {
        /* 110 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(1);
        /* 111 */
        hash.combine(this.pid.hashCode(), this.module.hashCode());
        /* 112 */
        hash.combine(this.arity);
        /* 113 */
        if (this.md5 != null) {
            /* 114 */
            hash.combine(this.md5);

        }
        /* 116 */
        hash.combine(this.index);
        /* 117 */
        hash.combine(this.uniq);
        /* 118 */
        if (this.freeVars != null) {
            /* 119 */
            for (OtpErlangObject o : this.freeVars) {
                /* 120 */
                hash.combine(o.hashCode(), 1);

            }

        }
        /* 123 */
        return hash.valueOf();

    }


    /* 128 */
    public String toString() {
        return "#Fun<" + this.module + "." + this.old_index + "." + this.uniq + ">";
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangFun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */