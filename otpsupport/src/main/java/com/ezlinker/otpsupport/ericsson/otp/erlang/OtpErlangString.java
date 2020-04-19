
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.nio.charset.StandardCharsets;


public class OtpErlangString
        extends OtpErlangObject {
    private static final long serialVersionUID = -7053595217604929233L;
    private final String str;


    /*  37 */
    public OtpErlangString(String str) {
        this.str = str;
    }


    public OtpErlangString(OtpErlangList list) throws OtpErlangException {
        /*  50 */
        String s = list.stringValue();
        /*  51 */
        int n = s.length();
        int i;
        /*  52 */
        for (i = 0; i < n; i = s.offsetByCodePoints(i, 1)) {
            /*  53 */
            int cp = s.codePointAt(i);
            /*  54 */
            if (!isValidCodePoint(cp)) {
                /*  55 */
                throw new OtpErlangRangeException("Invalid CodePoint: " + cp);

            }

        }
        /*  58 */
        this.str = s;

    }


    /*  74 */
    public OtpErlangString(OtpInputStream buf) throws OtpErlangDecodeException {
        this.str = buf.read_string();
    }


    public static int[] stringToCodePoints(String s) {
        /* 154 */
        int m = s.codePointCount(0, s.length());
        /* 155 */
        int[] codePoints = new int[m];
        /* 156 */
        int j = 0;
        int offset;
        /* 157 */
        for (offset = 0; offset < s.length(); ) {
            /* 158 */
            int codepoint = s.codePointAt(offset);
            /* 159 */
            codePoints[j++] = codepoint;
            /* 160 */
            offset += Character.charCount(codepoint);

        }
        /* 162 */
        return codePoints;

    }


    /* 179 */
    public static boolean isValidCodePoint(int cp) {
        return (cp >>> 16 <= 16 && (cp & 0xFFFFF800) != 55296);
    }


    public static String newString(byte[] bytes) {

        /* 191 */
        return new String(bytes, StandardCharsets.ISO_8859_1);
        /* 192 */

    }


    /*  86 */
    public String stringValue() {
        return this.str;
    }


    /*  99 */
    public String toString() {
        return "\"" + this.str + "\"";
    }


    /* 112 */
    public void encode(OtpOutputStream buf) {
        buf.write_string(this.str);
    }


    public boolean equals(Object o) {
        /* 129 */
        if (o instanceof String)
            /* 130 */ return (this.str.compareTo((String) o) == 0);
        /* 131 */
        if (o instanceof OtpErlangString) {
            /* 132 */
            return (this.str.compareTo(((OtpErlangString) o).str) == 0);

        }

        /* 135 */
        return false;

    }


    /* 140 */
    protected int doHashCode() {
        return this.str.hashCode();
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */