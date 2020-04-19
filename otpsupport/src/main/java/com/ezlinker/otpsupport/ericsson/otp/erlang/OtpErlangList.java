
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.util.Iterator;
import java.util.NoSuchElementException;


public class OtpErlangList
        extends OtpErlangObject
        implements Iterable<OtpErlangObject> {
    private static final long serialVersionUID = 5999112769036676548L;
    /*  37 */   private static final OtpErlangObject[] NO_ELEMENTS = new OtpErlangObject[0];

    private final OtpErlangObject[] elems;

    /*  41 */   private OtpErlangObject lastTail = null;


    /*  47 */
    public OtpErlangList() {
        this.elems = NO_ELEMENTS;
    }


    public OtpErlangList(String str) {
        /*  58 */
        if (str == null || str.length() == 0) {
            /*  59 */
            this.elems = NO_ELEMENTS;

        } else {
            /*  61 */
            int[] codePoints = OtpErlangString.stringToCodePoints(str);
            /*  62 */
            this.elems = new OtpErlangObject[codePoints.length];
            /*  63 */
            for (int i = 0; i < this.elems.length; i++) {
                /*  64 */
                this.elems[i] = new OtpErlangInt(codePoints[i]);

            }

        }

    }


    /*  76 */
    public OtpErlangList(OtpErlangObject elem) {
        this.elems = new OtpErlangObject[]{elem};
    }


    /*  86 */
    public OtpErlangList(OtpErlangObject[] elems) {
        this(elems, 0, elems.length);
    }


    public OtpErlangList(OtpErlangObject[] elems, OtpErlangObject lastTail) throws OtpErlangException {
        /* 100 */
        this(elems, 0, elems.length);
        /* 101 */
        if (elems.length == 0 && lastTail != null) {
            /* 102 */
            throw new OtpErlangException("Bad list, empty head, non-empty tail");

        }
        /* 104 */
        this.lastTail = lastTail;

    }


    public OtpErlangList(OtpErlangObject[] elems, int start, int count) {
        /* 119 */
        if (elems != null && count > 0) {
            /* 120 */
            this.elems = new OtpErlangObject[count];
            /* 121 */
            System.arraycopy(elems, start, this.elems, 0, count);

        } else {
            /* 123 */
            this.elems = NO_ELEMENTS;

        }

    }


    public OtpErlangList(OtpInputStream buf) throws OtpErlangDecodeException {
        /* 140 */
        int arity = buf.read_list_head();
        /* 141 */
        if (arity > 0) {
            /* 142 */
            this.elems = new OtpErlangObject[arity];
            /* 143 */
            for (int i = 0; i < arity; i++) {
                /* 144 */
                this.elems[i] = buf.read_any();

            }

            /* 147 */
            if (buf.peek1() == 106) {
                /* 148 */
                buf.read_nil();

            } else {
                /* 150 */
                this.lastTail = buf.read_any();

            }

        } else {
            /* 153 */
            this.elems = NO_ELEMENTS;

        }

    }


    /* 163 */
    public int arity() {
        return this.elems.length;
    }


    public OtpErlangObject elementAt(int i) {
        /* 176 */
        if (i >= arity() || i < 0) {
            /* 177 */
            return null;

        }
        /* 179 */
        return this.elems[i];

    }


    public OtpErlangObject[] elements() {
        /* 188 */
        if (arity() == 0) {
            /* 189 */
            return NO_ELEMENTS;

        }
        /* 191 */
        OtpErlangObject[] res = new OtpErlangObject[arity()];
        /* 192 */
        System.arraycopy(this.elems, 0, res, 0, res.length);
        /* 193 */
        return res;

    }


    /* 204 */
    public String toString() {
        return toString(0);
    }


    protected String toString(int start) {
        /* 208 */
        StringBuffer s = new StringBuffer();
        /* 209 */
        s.append("[");

        /* 211 */
        for (int i = start; i < arity(); i++) {
            /* 212 */
            if (i > start) {
                /* 213 */
                s.append(",");

            }
            /* 215 */
            s.append(this.elems[i].toString());

        }
        /* 217 */
        if (this.lastTail != null) {
            /* 218 */
            s.append("|").append(this.lastTail.toString());

        }
        /* 220 */
        s.append("]");

        /* 222 */
        return s.toString();

    }


    /* 237 */
    public void encode(OtpOutputStream buf) {
        encode(buf, 0);
    }


    protected void encode(OtpOutputStream buf, int start) {
        /* 241 */
        int arity = arity() - start;

        /* 243 */
        if (arity > 0) {
            /* 244 */
            buf.write_list_head(arity);

            /* 246 */
            for (int i = start; i < arity + start; i++) {
                /* 247 */
                buf.write_any(this.elems[i]);

            }

        }
        /* 250 */
        if (this.lastTail == null) {
            /* 251 */
            buf.write_nil();

        } else {
            /* 253 */
            buf.write_any(this.lastTail);

        }

    }


    public boolean equals(Object o) {
        /* 276 */
        if (!(o instanceof OtpErlangList)) {
            /* 277 */
            return false;

        }

        /* 280 */
        OtpErlangList l = (OtpErlangList) o;

        /* 282 */
        int a = arity();
        /* 283 */
        if (a != l.arity()) {
            /* 284 */
            return false;

        }
        /* 286 */
        for (int i = 0; i < a; i++) {
            /* 287 */
            if (!elementAt(i).equals(l.elementAt(i))) {
                /* 288 */
                return false;

            }

        }
        /* 291 */
        OtpErlangObject otherTail = l.getLastTail();
        /* 292 */
        if (getLastTail() == null && otherTail == null) {
            /* 293 */
            return true;

        }
        /* 295 */
        if (getLastTail() == null) {
            /* 296 */
            return false;

        }
        /* 298 */
        return getLastTail().equals(l.getLastTail());

    }


    public <T> boolean match(OtpErlangObject term, T bindings) {
        /* 303 */
        if (!(term instanceof OtpErlangList)) {
            /* 304 */
            return false;

        }
        /* 306 */
        OtpErlangList that = (OtpErlangList) term;

        /* 308 */
        int thisArity = arity();
        /* 309 */
        int thatArity = that.arity();
        /* 310 */
        OtpErlangObject thisTail = getLastTail();
        /* 311 */
        OtpErlangObject thatTail = that.getLastTail();

        /* 313 */
        if (thisTail == null) {
            /* 314 */
            if (thisArity != thatArity || thatTail != null) {
                /* 315 */
                return false;

            }

        }
        /* 318 */
        else if (thisArity > thatArity) {
            /* 319 */
            return false;

        }

        /* 322 */
        for (int i = 0; i < thisArity; i++) {
            /* 323 */
            if (!elementAt(i).match(that.elementAt(i), bindings)) {
                /* 324 */
                return false;

            }

        }
        /* 327 */
        if (thisTail == null) {
            /* 328 */
            return true;

        }
        /* 330 */
        return thisTail.match(that.getNthTail(thisArity), bindings);

    }


    public <T> OtpErlangObject bind(T binds) throws OtpErlangException {
        /* 335 */
        OtpErlangList list = (OtpErlangList) clone();

        /* 337 */
        int a = list.elems.length;
        /* 338 */
        for (int i = 0; i < a; i++) {
            /* 339 */
            list.elems[i] = list.elems[i].bind(binds);

        }

        /* 342 */
        if (list.lastTail != null) {
            /* 343 */
            list.lastTail = list.lastTail.bind(binds);

        }

        /* 346 */
        return list;

    }


    /* 350 */
    public OtpErlangObject getLastTail() {
        return this.lastTail;
    }


    protected int doHashCode() {
        /* 355 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(4);
        /* 356 */
        int a = arity();
        /* 357 */
        if (a == 0) {
            /* 358 */
            return -826096594;

        }
        /* 360 */
        for (int i = 0; i < a; i++) {
            /* 361 */
            hash.combine(elementAt(i).hashCode());

        }
        /* 363 */
        OtpErlangObject t = getLastTail();
        /* 364 */
        if (t != null) {
            /* 365 */
            int h = t.hashCode();
            /* 366 */
            hash.combine(h, h);

        }
        /* 368 */
        return hash.valueOf();

    }


    public Object clone() {

        try {
            /* 374 */
            return new OtpErlangList(elements(), getLastTail());
            /* 375 */
        } catch (OtpErlangException e) {
            /* 376 */
            throw new AssertionError(this);

        }

    }


    /* 381 */
    public Iterator<OtpErlangObject> iterator() {
        return iterator(0);
    }


    /* 385 */
    private Iterator<OtpErlangObject> iterator(int start) {
        return new Itr(start);
    }


    /* 392 */
    public boolean isProper() {
        return (this.lastTail == null);
    }


    public OtpErlangObject getHead() {
        /* 396 */
        if (arity() > 0) {
            /* 397 */
            return this.elems[0];

        }
        /* 399 */
        return null;

    }


    /* 403 */
    public OtpErlangObject getTail() {
        return getNthTail(1);
    }


    public OtpErlangObject getNthTail(int n) {
        /* 407 */
        int arity = arity();
        /* 408 */
        if (arity >= n) {
            /* 409 */
            if (arity == n && this.lastTail != null) {
                /* 410 */
                return this.lastTail;

            }
            /* 412 */
            return new SubList(this, n);

        }
        /* 414 */
        return null;

    }


    public String stringValue() throws OtpErlangException {
        /* 438 */
        if (!isProper()) {
            /* 439 */
            throw new OtpErlangException("Non-proper list: " + this);

        }
        /* 441 */
        int[] values = new int[arity()];
        /* 442 */
        for (int i = 0; i < values.length; i++) {
            /* 443 */
            OtpErlangObject o = elementAt(i);
            /* 444 */
            if (!(o instanceof OtpErlangLong)) {
                /* 445 */
                throw new OtpErlangException("Non-integer term: " + o);

            }
            /* 447 */
            OtpErlangLong l = (OtpErlangLong) o;
            /* 448 */
            values[i] = l.intValue();

        }
        /* 450 */
        return new String(values, 0, values.length);

    }


    public static class SubList
            extends OtpErlangList {
        private static final long serialVersionUID = 5999112769036676548L;
        private final int start;
        private final OtpErlangList parent;


        private SubList(OtpErlangList parent, int start) {
            /* 462 */
            this.parent = parent;
            /* 463 */
            this.start = start;

        }


        /* 468 */
        public int arity() {
            return this.parent.arity() - this.start;
        }


        /* 473 */
        public OtpErlangObject elementAt(int i) {
            return this.parent.elementAt(i + this.start);
        }


        public OtpErlangObject[] elements() {
            /* 478 */
            int n = this.parent.arity() - this.start;
            /* 479 */
            OtpErlangObject[] res = new OtpErlangObject[n];
            /* 480 */
            for (int i = 0; i < res.length; i++) {
                /* 481 */
                res[i] = this.parent.elementAt(i + this.start);

            }
            /* 483 */
            return res;

        }


        /* 488 */
        public boolean isProper() {
            return this.parent.isProper();
        }


        /* 493 */
        public OtpErlangObject getHead() {
            return this.parent.elementAt(this.start);
        }


        /* 498 */
        public OtpErlangObject getNthTail(int n) {
            return this.parent.getNthTail(n + this.start);
        }


        /* 503 */
        public String toString() {
            return this.parent.toString(this.start);
        }


        /* 508 */
        public void encode(OtpOutputStream stream) {
            this.parent.encode(stream, this.start);
        }


        /* 513 */
        public OtpErlangObject getLastTail() {
            return this.parent.getLastTail();
        }


        /* 518 */
        public Iterator<OtpErlangObject> iterator() {
            return this.parent.iterator(this.start);
        }

    }


    private class Itr
            implements Iterator<OtpErlangObject> {
        private int cursor;


        /* 529 */
        private Itr(int cursor) {
            this.cursor = cursor;
        }


        /* 533 */
        public boolean hasNext() {
            return (this.cursor < OtpErlangList.this.elems.length);
        }


        public OtpErlangObject next() {

            try {
                /* 538 */
                return OtpErlangList.this.elems[this.cursor++];
                /* 539 */
            } catch (IndexOutOfBoundsException e) {
                /* 540 */
                throw new NoSuchElementException();

            }

        }


        /* 545 */
        public void remove() {
            throw new UnsupportedOperationException("OtpErlangList cannot be modified!");
        }

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */