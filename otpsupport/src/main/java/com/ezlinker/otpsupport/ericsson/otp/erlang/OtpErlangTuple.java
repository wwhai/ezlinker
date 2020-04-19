
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpErlangTuple
        extends OtpErlangObject {
    private static final long serialVersionUID = 9163498658004915935L;
    /*  35 */   private static final OtpErlangObject[] NO_ELEMENTS = new OtpErlangObject[0];

    /*  37 */   private OtpErlangObject[] elems = NO_ELEMENTS;


    public OtpErlangTuple(OtpErlangObject elem) {
        /*  49 */
        if (elem == null) {
            /*  50 */
            throw new IllegalArgumentException("Tuple element cannot be null");

        }

        /*  53 */
        this.elems = new OtpErlangObject[]{elem};

    }


    /*  66 */
    public OtpErlangTuple(OtpErlangObject[] elems) {
        this(elems, 0, elems.length);
    }


    public OtpErlangTuple(OtpErlangObject[] elems, int start, int count) {
        /*  84 */
        if (elems == null) {
            /*  85 */
            throw new IllegalArgumentException("Tuple content can't be null");

        }
        /*  87 */
        if (count < 1) {
            /*  88 */
            this.elems = NO_ELEMENTS;

        } else {
            /*  90 */
            this.elems = new OtpErlangObject[count];
            /*  91 */
            for (int i = 0; i < count; i++) {
                /*  92 */
                if (elems[start + i] != null) {
                    /*  93 */
                    this.elems[i] = elems[start + i];

                } else {
                    /*  95 */
                    throw new IllegalArgumentException("Tuple element cannot be null (element" + (start + i) + ")");

                }

            }

        }

    }


    public OtpErlangTuple(OtpInputStream buf) throws OtpErlangDecodeException {
        /* 116 */
        int arity = buf.read_tuple_head();

        /* 118 */
        if (arity > 0) {
            /* 119 */
            this.elems = new OtpErlangObject[arity];

            /* 121 */
            for (int i = 0; i < arity; i++) {
                /* 122 */
                this.elems[i] = buf.read_any();

            }

        } else {
            /* 125 */
            this.elems = NO_ELEMENTS;

        }

    }


    /* 135 */
    public int arity() {
        return this.elems.length;
    }


    public OtpErlangObject elementAt(int i) {
        /* 148 */
        if (i >= arity() || i < 0) {
            /* 149 */
            return null;

        }
        /* 151 */
        return this.elems[i];

    }


    public OtpErlangObject[] elements() {
        /* 160 */
        OtpErlangObject[] res = new OtpErlangObject[arity()];
        /* 161 */
        System.arraycopy(this.elems, 0, res, 0, res.length);
        /* 162 */
        return res;

    }


    public String toString() {
        /* 173 */
        StringBuffer s = new StringBuffer();
        /* 174 */
        int arity = this.elems.length;

        /* 176 */
        s.append("{");

        /* 178 */
        for (int i = 0; i < arity; i++) {
            /* 179 */
            if (i > 0) {
                /* 180 */
                s.append(",");

            }
            /* 182 */
            s.append(this.elems[i].toString());

        }

        /* 185 */
        s.append("}");

        /* 187 */
        return s.toString();

    }


    public void encode(OtpOutputStream buf) {
        /* 198 */
        int arity = this.elems.length;

        /* 200 */
        buf.write_tuple_head(arity);

        /* 202 */
        for (int i = 0; i < arity; i++) {
            /* 203 */
            buf.write_any(this.elems[i]);

        }

    }


    public boolean equals(Object o) {
        /* 219 */
        if (!(o instanceof OtpErlangTuple)) {
            /* 220 */
            return false;

        }

        /* 223 */
        OtpErlangTuple t = (OtpErlangTuple) o;
        /* 224 */
        int a = arity();

        /* 226 */
        if (a != t.arity()) {
            /* 227 */
            return false;

        }

        /* 230 */
        for (int i = 0; i < a; i++) {
            /* 231 */
            if (!this.elems[i].equals(t.elems[i])) {
                /* 232 */
                return false;

            }

        }

        /* 236 */
        return true;

    }


    public <T> boolean match(OtpErlangObject term, T bindings) {
        /* 241 */
        if (!(term instanceof OtpErlangTuple)) {
            /* 242 */
            return false;

        }
        /* 244 */
        OtpErlangTuple t = (OtpErlangTuple) term;
        /* 245 */
        int a = this.elems.length;
        /* 246 */
        if (a != t.elems.length) {
            /* 247 */
            return false;

        }
        /* 249 */
        for (int i = 0; i < a; i++) {
            /* 250 */
            if (!this.elems[i].match(t.elems[i], bindings)) {
                /* 251 */
                return false;

            }

        }
        /* 254 */
        return true;

    }


    public <T> OtpErlangObject bind(T binds) throws OtpErlangException {
        /* 259 */
        OtpErlangTuple tuple = (OtpErlangTuple) clone();
        /* 260 */
        int a = tuple.elems.length;
        /* 261 */
        for (int i = 0; i < a; i++) {
            /* 262 */
            OtpErlangObject e = tuple.elems[i];
            /* 263 */
            tuple.elems[i] = e.bind(binds);

        }
        /* 265 */
        return tuple;

    }


    protected int doHashCode() {
        /* 270 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(9);
        /* 271 */
        int a = arity();
        /* 272 */
        hash.combine(a);
        /* 273 */
        for (int i = 0; i < a; i++) {
            /* 274 */
            hash.combine(this.elems[i].hashCode());

        }
        /* 276 */
        return hash.valueOf();

    }


    public Object clone() {
        /* 281 */
        OtpErlangTuple newTuple = (OtpErlangTuple) super.clone();
        /* 282 */
        newTuple.elems = this.elems.clone();
        /* 283 */
        return newTuple;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangTuple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */