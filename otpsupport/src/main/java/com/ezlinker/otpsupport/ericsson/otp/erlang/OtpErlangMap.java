
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class OtpErlangMap
        extends OtpErlangObject {
    private static final long serialVersionUID = -6410770117696198497L;
    private OtpMap map;


    /*  55 */
    public OtpErlangMap() {
        this.map = new OtpMap();
    }


    /*  71 */
    public OtpErlangMap(OtpErlangObject[] keys, OtpErlangObject[] values) {
        this(keys, 0, keys.length, values, 0, values.length);
    }


    public OtpErlangMap(OtpErlangObject[] keys, int kstart, int kcount, OtpErlangObject[] values, int vstart, int vcount) {
        /*  98 */
        if (keys == null || values == null) {
            /*  99 */
            throw new IllegalArgumentException("Map content can't be null");

        }
        /* 101 */
        if (kcount != vcount) {
            /* 102 */
            throw new IllegalArgumentException("Map keys and values must have same arity");

        }

        /* 105 */
        this.map = new OtpMap();

        /* 107 */
        for (int i = 0; i < vcount; i++) {
            /* 108 */
            OtpErlangObject key;
            if ((key = keys[kstart + i]) == null) {
                /* 109 */
                throw new IllegalArgumentException("Map key cannot be null (element" + (kstart + i) + ")");

            }

            OtpErlangObject val;
            /* 112 */
            if ((val = values[vstart + i]) == null) {
                /* 113 */
                throw new IllegalArgumentException("Map value cannot be null (element" + (vstart + i) + ")");

            }


            /* 117 */
            put(key, val);

        }

    }


    public OtpErlangMap(OtpInputStream buf) throws OtpErlangDecodeException {
        /* 134 */
        int arity = buf.read_map_head();

        /* 136 */
        if (arity > 0) {
            /* 137 */
            this.map = new OtpMap();
            /* 138 */
            for (int i = 0; i < arity; i++) {

                /* 140 */
                OtpErlangObject key = buf.read_any();
                /* 141 */
                OtpErlangObject val = buf.read_any();
                /* 142 */
                put(key, val);

            }

        } else {
            /* 145 */
            this.map = new OtpMap();

        }

    }


    /* 155 */
    public int arity() {
        return this.map.size();
    }


    /* 170 */
    public OtpErlangObject put(OtpErlangObject key, OtpErlangObject value) {
        return this.map.put(key, value);
    }


    /* 181 */
    public OtpErlangObject remove(OtpErlangObject key) {
        return this.map.remove(key);
    }


    /* 193 */
    public OtpErlangObject get(OtpErlangObject key) {
        return this.map.get(key);
    }


    /* 202 */
    public OtpErlangObject[] keys() {
        return this.map.keySet().toArray(new OtpErlangObject[arity()]);
    }


    /* 211 */
    public OtpErlangObject[] values() {
        return this.map.values().toArray(new OtpErlangObject[arity()]);
    }


    /* 220 */
    public Set<Map.Entry<OtpErlangObject, OtpErlangObject>> entrySet() {
        return this.map.entrySet();
    }


    public String toString() {
        /* 230 */
        StringBuffer s = new StringBuffer();

        /* 232 */
        s.append("#{");

        /* 234 */
        boolean first = true;
        /* 235 */
        for (Map.Entry<OtpErlangObject, OtpErlangObject> e : entrySet()) {
            /* 236 */
            if (first) {
                /* 237 */
                first = false;

            } else {
                /* 239 */
                s.append(",");

            }
            /* 241 */
            s.append(e.getKey().toString());
            /* 242 */
            s.append(" => ");
            /* 243 */
            s.append(e.getValue().toString());

        }

        /* 246 */
        s.append("}");

        /* 248 */
        return s.toString();

    }


    public void encode(OtpOutputStream buf) {
        /* 259 */
        int arity = arity();

        /* 261 */
        buf.write_map_head(arity);

        /* 263 */
        for (Map.Entry<OtpErlangObject, OtpErlangObject> e : entrySet()) {
            /* 264 */
            buf.write_any(e.getKey());
            /* 265 */
            buf.write_any(e.getValue());

        }

    }


    public boolean equals(Object o) {
        /* 281 */
        if (!(o instanceof OtpErlangMap)) {
            /* 282 */
            return false;

        }

        /* 285 */
        OtpErlangMap t = (OtpErlangMap) o;
        /* 286 */
        int a = arity();

        /* 288 */
        if (a != t.arity()) {
            /* 289 */
            return false;

        }
        /* 291 */
        if (a == 0) {
            /* 292 */
            return true;

        }


        /* 296 */
        for (Map.Entry<OtpErlangObject, OtpErlangObject> e : entrySet()) {
            /* 297 */
            OtpErlangObject key = e.getKey();
            /* 298 */
            OtpErlangObject val = e.getValue();
            /* 299 */
            OtpErlangObject v = t.get(key);
            /* 300 */
            if (v == null || !val.equals(v)) {
                /* 301 */
                return false;

            }

        }

        /* 305 */
        return true;

    }


    public <T> boolean match(OtpErlangObject term, T binds) {
        /* 310 */
        if (!(term instanceof OtpErlangMap)) {
            /* 311 */
            return false;

        }

        /* 314 */
        OtpErlangMap t = (OtpErlangMap) term;
        /* 315 */
        int a = arity();

        /* 317 */
        if (a > t.arity()) {
            /* 318 */
            return false;

        }
        /* 320 */
        if (a == 0) {
            /* 321 */
            return true;

        }


        /* 325 */
        for (Map.Entry<OtpErlangObject, OtpErlangObject> e : entrySet()) {
            /* 326 */
            OtpErlangObject key = e.getKey();
            /* 327 */
            OtpErlangObject val = e.getValue();
            /* 328 */
            OtpErlangObject v = t.get(key);
            /* 329 */
            if (v == null || !val.match(v, binds)) {
                /* 330 */
                return false;

            }

        }

        /* 334 */
        return true;

    }


    public <T> OtpErlangObject bind(T binds) throws OtpErlangException {
        /* 339 */
        OtpErlangMap ret = new OtpErlangMap();


        /* 342 */
        for (Map.Entry<OtpErlangObject, OtpErlangObject> e : entrySet()) {
            /* 343 */
            OtpErlangObject key = e.getKey();
            /* 344 */
            OtpErlangObject val = e.getValue();
            /* 345 */
            ret.put(key, val.bind(binds));

        }

        /* 348 */
        return ret;

    }


    protected int doHashCode() {
        /* 353 */
        OtpErlangObject.Hash hash = new OtpErlangObject.Hash(9);
        /* 354 */
        hash.combine(this.map.hashCode());
        /* 355 */
        return hash.valueOf();

    }


    public Object clone() {
        /* 361 */
        OtpErlangMap newMap = (OtpErlangMap) super.clone();
        /* 362 */
        newMap.map = (OtpMap) this.map.clone();
        /* 363 */
        return newMap;

    }


    private static class OtpMap
            extends LinkedHashMap<OtpErlangObject, OtpErlangObject> {
        private static final long serialVersionUID = -2666505810905455082L;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpErlangMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */