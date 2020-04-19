
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangAtom
        extends OtpErlangObject {
    public static final int maxAtomLength = 255;
    private static final long serialVersionUID = -3204386396807876641L;
    private final String atom;


    public OtpErlangAtom(String atom) {
        /*  47 */
        if (atom == null) {
            /*  48 */
            throw new IllegalArgumentException("null string value");

        }

        /*  51 */
        if (atom.codePointCount(0, atom.length()) > 255) {
            /*  52 */
            throw new IllegalArgumentException("Atom may not exceed 255 characters: " + atom);

        }

        /*  55 */
        this.atom = atom;

    }


    /*  71 */
    public OtpErlangAtom(OtpInputStream buf) throws OtpErlangDecodeException {
        this.atom = buf.read_atom();
    }


    /*  78 */
    public OtpErlangAtom(boolean t) {
        this.atom = String.valueOf(t);
    }


    /*  90 */
    public String atomValue() {
        return this.atom;
    }


    /* 102 */
    public boolean booleanValue() {
        return Boolean.valueOf(atomValue()).booleanValue();
    }


    public String toString() {
        /* 117 */
        if (atomNeedsQuoting(this.atom)) {
            /* 118 */
            return "'" + escapeSpecialChars(this.atom) + "'";

        }
        /* 120 */
        return this.atom;

    }


    public boolean equals(Object o) {
        /* 134 */
        if (!(o instanceof OtpErlangAtom)) {
            /* 135 */
            return false;

        }

        /* 138 */
        OtpErlangAtom other = (OtpErlangAtom) o;
        /* 139 */
        return (this.atom.compareTo(other.atom) == 0);

    }


    /* 144 */
    protected int doHashCode() {
        return this.atom.hashCode();
    }


    /* 155 */
    public void encode(OtpOutputStream buf) {
        buf.write_atom(this.atom);
    }


    /* 160 */
    private boolean isErlangDigit(char c) {
        return (c >= '0' && c <= '9');
    }


    /* 164 */
    private boolean isErlangUpper(char c) {
        return ((c >= 'A' && c <= 'Z') || c == '_');
    }


    /* 168 */
    private boolean isErlangLower(char c) {
        return (c >= 'a' && c <= 'z');
    }


    /* 172 */
    private boolean isErlangLetter(char c) {
        return (isErlangLower(c) || isErlangUpper(c));
    }


    private boolean atomNeedsQuoting(String s) {
        /* 179 */
        if (s.length() == 0) {
            /* 180 */
            return true;

        }
        /* 182 */
        if (!isErlangLower(s.charAt(0))) {
            /* 183 */
            return true;

        }

        /* 186 */
        int len = s.length();
        /* 187 */
        for (int i = 1; i < len; i++) {
            /* 188 */
            char c = s.charAt(i);

            /* 190 */
            if (!isErlangLetter(c) && !isErlangDigit(c) && c != '@') {
                /* 191 */
                return true;

            }

        }
        /* 194 */
        return false;

    }


    private String escapeSpecialChars(String s) {
        /* 204 */
        StringBuffer so = new StringBuffer();

        /* 206 */
        int len = s.length();
        /* 207 */
        for (int i = 0; i < len; i++) {
            /* 208 */
            char c = s.charAt(i);







            /* 216 */
            switch (c) {


                case '\b':
                    /* 219 */
                    so.append("\\b");

                    break;


                case '':
                    /* 223 */
                    so.append("\\d");

                    break;


                case '\033':
                    /* 227 */
                    so.append("\\e");

                    break;


                case '\f':
                    /* 231 */
                    so.append("\\f");

                    break;


                case '\n':
                    /* 235 */
                    so.append("\\n");

                    break;


                case '\r':
                    /* 239 */
                    so.append("\\r");

                    break;


                case '\t':
                    /* 243 */
                    so.append("\\t");

                    break;


                case '\013':
                    /* 247 */
                    so.append("\\v");

                    break;


                case '\\':
                    /* 251 */
                    so.append("\\\\");

                    break;


                case '\'':
                    /* 255 */
                    so.append("\\'");

                    break;


                case '"':
                    /* 259 */
                    so.append("\\\"");

                    break;


                default:
                    /* 264 */
                    if (c < '\027') {

                        /* 266 */
                        so.append("\\^" + (char) (64 + c));
                        break;
                        /* 267 */
                    }
                    if (c > '~') {

                        /* 269 */
                        so.append("\\" + Integer.toOctalString(c));

                        break;

                    }
                    /* 272 */
                    so.append(c);

                    break;

            }

        }
        /* 276 */
        return new String(so);

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangAtom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */