
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangDouble
        extends OtpErlangObject {
    private static final long serialVersionUID = 132947104811974021L;
    private final double d;


    /*  38 */
    public OtpErlangDouble(double d) {
        this.d = d;
    }


    /*  54 */
    public OtpErlangDouble(OtpInputStream buf) throws OtpErlangDecodeException {
        this.d = buf.read_double();
    }


    /*  63 */
    public double doubleValue() {
        return this.d;
    }


    public float floatValue() throws OtpErlangRangeException {
        /*  75 */
        float f = (float) this.d;

        /*  77 */
        if (f != this.d) {
            /*  78 */
            throw new OtpErlangRangeException("Value too large for float: " + this.d);

        }

        /*  81 */
        return f;

    }


    /*  91 */
    public String toString() {
        return "" + this.d;
    }


    /* 102 */
    public void encode(OtpOutputStream buf) {
        buf.write_double(this.d);
    }


    public boolean equals(Object o) {
        /* 116 */
        if (!(o instanceof OtpErlangDouble)) {
            /* 117 */
            return false;

        }

        /* 120 */
        OtpErlangDouble other = (OtpErlangDouble) o;
        /* 121 */
        return (this.d == other.d);

    }


    protected int doHashCode() {
        /* 126 */
        Double v = new Double(this.d);
        /* 127 */
        return v.hashCode();

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */