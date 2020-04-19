//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ezlinker.otpsupport.ericsson.otp.erlang;

import java.io.Serializable;

public abstract class OtpErlangObject implements Serializable, Cloneable {
    static final long serialVersionUID = -8435938572339430044L;
    protected int hashCodeValue = 0;

    public OtpErlangObject() {
    }

    public static OtpErlangObject decode(OtpInputStream buf) throws OtpErlangDecodeException {
        return buf.read_any();
    }

    public abstract String toString();

    public abstract void encode(OtpOutputStream var1);

    public abstract boolean equals(Object var1);

    public <T> boolean match(OtpErlangObject term, T binds) {
        return this.equals(term);
    }

    public <T> OtpErlangObject bind(T binds) throws OtpErlangException {
        return this;
    }

    public int hashCode() {
        if (this.hashCodeValue == 0) {
            this.hashCodeValue = this.doHashCode();
        }

        return this.hashCodeValue;
    }

    protected int doHashCode() {
        return super.hashCode();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new InternalError(var2.toString());
        }
    }

    protected static final class Hash {
        private static final int[] HASH_CONST = new int[]{0, -1640531527, 1013904242, -626627285, 2027808484, 387276957, -1253254570, 1401181199, -239350328, -1879881855, 774553914, -865977613, 1788458156, 147926629, -1492604898, 1161830871};
        int[] abc = new int[]{0, 0, 0};

        protected Hash(int i) {
            this.abc[0] = this.abc[1] = HASH_CONST[i];
            this.abc[2] = 0;
        }

        private void mix() {
            int[] var10000 = this.abc;
            var10000[0] -= this.abc[1];
            var10000 = this.abc;
            var10000[0] -= this.abc[2];
            var10000 = this.abc;
            var10000[0] ^= this.abc[2] >>> 13;
            var10000 = this.abc;
            var10000[1] -= this.abc[2];
            var10000 = this.abc;
            var10000[1] -= this.abc[0];
            var10000 = this.abc;
            var10000[1] ^= this.abc[0] << 8;
            var10000 = this.abc;
            var10000[2] -= this.abc[0];
            var10000 = this.abc;
            var10000[2] -= this.abc[1];
            var10000 = this.abc;
            var10000[2] ^= this.abc[1] >>> 13;
            var10000 = this.abc;
            var10000[0] -= this.abc[1];
            var10000 = this.abc;
            var10000[0] -= this.abc[2];
            var10000 = this.abc;
            var10000[0] ^= this.abc[2] >>> 12;
            var10000 = this.abc;
            var10000[1] -= this.abc[2];
            var10000 = this.abc;
            var10000[1] -= this.abc[0];
            var10000 = this.abc;
            var10000[1] ^= this.abc[0] << 16;
            var10000 = this.abc;
            var10000[2] -= this.abc[0];
            var10000 = this.abc;
            var10000[2] -= this.abc[1];
            var10000 = this.abc;
            var10000[2] ^= this.abc[1] >>> 5;
            var10000 = this.abc;
            var10000[0] -= this.abc[1];
            var10000 = this.abc;
            var10000[0] -= this.abc[2];
            var10000 = this.abc;
            var10000[0] ^= this.abc[2] >>> 3;
            var10000 = this.abc;
            var10000[1] -= this.abc[2];
            var10000 = this.abc;
            var10000[1] -= this.abc[0];
            var10000 = this.abc;
            var10000[1] ^= this.abc[0] << 10;
            var10000 = this.abc;
            var10000[2] -= this.abc[0];
            var10000 = this.abc;
            var10000[2] -= this.abc[1];
            var10000 = this.abc;
            var10000[2] ^= this.abc[1] >>> 15;
        }

        protected void combine(int a) {
            int[] var10000 = this.abc;
            var10000[0] += a;
            this.mix();
        }

        protected void combine(long a) {
            this.combine((int) (a >>> 32), (int) a);
        }

        protected void combine(int a, int b) {
            int[] var10000 = this.abc;
            var10000[0] += a;
            var10000 = this.abc;
            var10000[1] += b;
            this.mix();
        }

        protected void combine(byte[] b) {
            int j = 0;

            int[] var10000;
            int k;
            for (k = 0; j + 4 < b.length; k %= 3) {
                var10000 = this.abc;
                var10000[k] += (b[j + 0] & 255) + (b[j + 1] << 8 & '\uff00') + (b[j + 2] << 16 & 16711680) + (b[j + 3] << 24);
                this.mix();
                j += 4;
                ++k;
            }

            int n = 0;

            for (int m = 255; j < b.length; m <<= 8) {
                var10000 = this.abc;
                var10000[k] += b[j] << n & m;
                ++j;
                n += 8;
            }

            this.mix();
        }

        protected int valueOf() {
            return this.abc[2];
        }
    }
}
