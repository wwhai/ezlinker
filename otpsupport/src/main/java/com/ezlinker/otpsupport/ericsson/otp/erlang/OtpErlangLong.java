
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.math.BigInteger;


public class OtpErlangLong
        extends OtpErlangObject {
    private static final long serialVersionUID = 1610466859236755096L;
    private long val;
    /*  38 */   private BigInteger bigVal = null;


    /*  47 */
    public OtpErlangLong(long l) {
        this.val = l;
    }


    public OtpErlangLong(BigInteger v) {
        /*  57 */
        if (v == null) {
            /*  58 */
            throw new NullPointerException();

        }
        /*  60 */
        if (v.bitLength() < 64) {
            /*  61 */
            this.val = v.longValue();

        } else {
            /*  63 */
            this.bigVal = v;

        }

    }


    public OtpErlangLong(OtpInputStream buf) throws OtpErlangDecodeException {
        /*  80 */
        byte[] b = buf.read_integer_byte_array();

        try {
            /*  82 */
            this.val = OtpInputStream.byte_array_to_long(b, false);
            /*  83 */
        } catch (OtpErlangDecodeException e) {
            /*  84 */
            this.bigVal = new BigInteger(b);

        }

    }


    public BigInteger bigIntegerValue() {
        /*  94 */
        if (this.bigVal != null) {
            /*  95 */
            return this.bigVal;

        }
        /*  97 */
        return BigInteger.valueOf(this.val);

    }


    public long longValue() {
        /* 108 */
        if (this.bigVal != null) {
            /* 109 */
            return this.bigVal.longValue();

        }
        /* 111 */
        return this.val;

    }


    public boolean isLong() {
        /* 123 */
        if (this.bigVal != null) {
            /* 124 */
            return (this.bigVal.bitLength() < 64);

        }
        /* 126 */
        return true;

    }


    public boolean isULong() {
        /* 140 */
        if (this.bigVal != null) {
            /* 141 */
            return (this.bigVal.signum() >= 0 && this.bigVal.bitLength() <= 64);

        }
        /* 143 */
        return (this.val >= 0L);

    }


    public int bitLength() {
        /* 154 */
        if (this.bigVal != null) {
            /* 155 */
            return this.bigVal.bitLength();

        }
        /* 157 */
        if (this.val == 0L || this.val == -1L) {
            /* 158 */
            return 0;

        }

        /* 161 */
        int i = 32;
        /* 162 */
        long m = (1L << i) - 1L;
        /* 163 */
        if (this.val < 0L) {
            /* 164 */
            m ^= 0xFFFFFFFFFFFFFFFFL;
            int j;
            /* 165 */
            for (j = i >> 1; j > 0; j >>= 1) {
                /* 166 */
                if ((this.val | m) == this.val) {
                    /* 167 */
                    i -= j;
                    /* 168 */
                    m >>= j;

                } else {
                    /* 170 */
                    i += j;
                    /* 171 */
                    m <<= j;

                }

            }
            /* 174 */
            if ((this.val | m) != this.val)
                /* 175 */ i++;

        } else {

            int j;
            /* 178 */
            for (j = i >> 1; j > 0; j >>= 1) {
                /* 179 */
                if ((this.val & m) == this.val) {
                    /* 180 */
                    i -= j;
                    /* 181 */
                    m >>= j;

                } else {
                    /* 183 */
                    i += j;
                    /* 184 */
                    m = m << j | m;

                }

            }
            /* 187 */
            if ((this.val & m) != this.val) {
                /* 188 */
                i++;

            }

        }
        /* 191 */
        return i;

    }


    public int signum() {
        /* 200 */
        if (this.bigVal != null) {
            /* 201 */
            return this.bigVal.signum();

        }
        /* 203 */
        return (this.val > 0L) ? 1 : ((this.val < 0L) ? -1 : 0);

    }


    public int intValue() throws OtpErlangRangeException {
        /* 215 */
        long l = longValue();
        /* 216 */
        int i = (int) l;

        /* 218 */
        if (i != l) {
            /* 219 */
            throw new OtpErlangRangeException("Value too large for int: " + this.val);

        }

        /* 222 */
        return i;

    }


    public int uIntValue() throws OtpErlangRangeException {
        /* 235 */
        long l = longValue();
        /* 236 */
        int i = (int) l;

        /* 238 */
        if (i != l)
            /* 239 */ throw new OtpErlangRangeException("Value too large for int: " + this.val);
        /* 240 */
        if (i < 0) {
            /* 241 */
            throw new OtpErlangRangeException("Value not positive: " + this.val);

        }

        /* 244 */
        return i;

    }


    public short shortValue() throws OtpErlangRangeException {
        /* 256 */
        long l = longValue();
        /* 257 */
        short i = (short) (int) l;

        /* 259 */
        if (i != l) {
            /* 260 */
            throw new OtpErlangRangeException("Value too large for short: " + this.val);

        }


        /* 264 */
        return i;

    }


    public short uShortValue() throws OtpErlangRangeException {
        /* 277 */
        long l = longValue();
        /* 278 */
        short i = (short) (int) l;

        /* 280 */
        if (i != l) {
            /* 281 */
            throw new OtpErlangRangeException("Value too large for short: " + this.val);

        }
        /* 283 */
        if (i < 0) {
            /* 284 */
            throw new OtpErlangRangeException("Value not positive: " + this.val);

        }

        /* 287 */
        return i;

    }


    public char charValue() throws OtpErlangRangeException {
        /* 299 */
        long l = longValue();
        /* 300 */
        char i = (char) (int) l;

        /* 302 */
        if (i != l) {
            /* 303 */
            throw new OtpErlangRangeException("Value too large for char: " + this.val);

        }


        /* 307 */
        return i;

    }


    public byte byteValue() throws OtpErlangRangeException {
        /* 319 */
        long l = longValue();
        /* 320 */
        byte i = (byte) (int) l;

        /* 322 */
        if (i != l) {
            /* 323 */
            throw new OtpErlangRangeException("Value too large for byte: " + this.val);

        }


        /* 327 */
        return i;

    }


    public String toString() {
        /* 337 */
        if (this.bigVal != null) {
            /* 338 */
            return "" + this.bigVal;

        }
        /* 340 */
        return "" + this.val;

    }


    public void encode(OtpOutputStream buf) {
        /* 352 */
        if (this.bigVal != null) {
            /* 353 */
            buf.write_big_integer(this.bigVal);

        } else {
            /* 355 */
            buf.write_long(this.val);

        }

    }


    public boolean equals(Object o) {
        /* 370 */
        if (!(o instanceof OtpErlangLong)) {
            /* 371 */
            return false;

        }

        /* 374 */
        OtpErlangLong that = (OtpErlangLong) o;

        /* 376 */
        if (this.bigVal != null && that.bigVal != null)
            /* 377 */ return this.bigVal.equals(that.bigVal);
        /* 378 */
        if (this.bigVal == null && that.bigVal == null) {
            /* 379 */
            return (this.val == that.val);

        }
        /* 381 */
        return false;

    }


    protected int doHashCode() {
        /* 386 */
        if (this.bigVal != null) {
            /* 387 */
            return this.bigVal.hashCode();

        }
        /* 389 */
        return BigInteger.valueOf(this.val).hashCode();

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */