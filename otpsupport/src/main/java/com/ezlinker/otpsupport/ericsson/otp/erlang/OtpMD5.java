
package com.ezlinker.otpsupport.ericsson.otp.erlang;


class OtpMD5 {
    static final long S11 = 7L;
    static final long S12 = 12L;
    static final long S13 = 17L;
    static final long S14 = 22L;
    static final long S21 = 5L;
    static final long S22 = 9L;
    static final long S23 = 14L;
    static final long S24 = 20L;
    static final long S31 = 4L;
    static final long S32 = 11L;
    static final long S33 = 16L;
    static final long S34 = 23L;
    static final long S41 = 6L;
    static final long S42 = 10L;
    static final long S43 = 15L;
    static final long S44 = 21L;
    /*  49 */   private final long[] state = new long[]{1732584193L, 4023233417L, 2562383102L, 271733878L};

    /*  51 */   private final long[] count = new long[]{0L, 0L};
    private final int[] buffer;


    public OtpMD5() {
        /*  55 */
        this.buffer = new int[64];

        /*  57 */
        for (int i = 0; i < 64; i++) {
            /*  58 */
            this.buffer[i] = 0;

        }

    }


    private int[] to_bytes(String s) {
        /*  63 */
        char[] tmp = s.toCharArray();
        /*  64 */
        int[] ret = new int[tmp.length];


        /*  67 */
        for (int i = 0; i < tmp.length; i++) {
            /*  68 */
            ret[i] = tmp[i] & 0xFF;

        }
        /*  70 */
        return ret;

    }


    private int[] clean_bytes(int[] bytes) {
        /*  74 */
        int[] ret = new int[bytes.length];


        /*  77 */
        for (int i = 0; i < bytes.length; i++) {
            /*  78 */
            ret[i] = bytes[i] & 0xFF;

        }
        /*  80 */
        return ret;

    }


    /*  88 */
    private long shl(long what, int steps) {
        return what << steps & 0xFFFFFFFFL;
    }


    /*  92 */
    private long shr(long what, int steps) {
        return what >>> steps;
    }


    /*  96 */
    private long plus(long a, long b) {
        return a + b & 0xFFFFFFFFL;
    }


    /* 100 */
    private long not(long x) {
        return (x ^ 0xFFFFFFFFFFFFFFFFL) & 0xFFFFFFFFL;
    }


    private void to_buffer(int to_start, int[] from, int from_start, int num) {
        /* 105 */
        int ix = num;
        /* 106 */
        int to_ix = to_start;
        /* 107 */
        int from_ix = from_start;
        /* 108 */
        while (ix-- > 0) {
            /* 109 */
            this.buffer[to_ix++] = from[from_ix++];

        }

    }


    private void do_update(int[] bytes) {
        /* 114 */
        int i, index = (int) (this.count[0] >>> 3L & 0x3FL);
        /* 115 */
        long inlen = bytes.length;
        /* 116 */
        long addcount = shl(inlen, 3);
        /* 117 */
        long partlen = (64 - index);


        /* 120 */
        this.count[0] = plus(this.count[0], addcount);

        /* 122 */
        if (this.count[0] < addcount) {
            /* 123 */
            this.count[1] = this.count[1] + 1L;

        }

        /* 126 */
        this.count[1] = plus(this.count[1], shr(inlen, 29));



        /* 130 */
        if (inlen >= partlen) {
            /* 131 */
            to_buffer(index, bytes, 0, (int) partlen);
            /* 132 */
            transform(this.buffer, 0);

            /* 134 */
            for (i = (int) partlen; (i + 63) < inlen; i += 64) {
                /* 135 */
                transform(bytes, i);

            }

            /* 138 */
            index = 0;

        } else {
            /* 140 */
            i = 0;

        }



        /* 145 */
        to_buffer(index, bytes, i, (int) inlen - i);

    }


    private void dumpstate() {
        /* 153 */
        System.out.println("state = {" + this.state[0] + ", " + this.state[1] + ", " + this.state[2] + ", " + this.state[3] + "}");

        /* 155 */
        System.out.println("count = {" + this.count[0] + ", " + this.count[1] + "}");
        /* 156 */
        System.out.print("buffer = {");

        /* 158 */
        for (int i = 0; i < 64; i++) {
            /* 159 */
            if (i > 0) {
                /* 160 */
                System.out.print(", ");

            }
            /* 162 */
            System.out.print(this.buffer[i]);

        }
        /* 164 */
        System.out.println("}");

    }


    /* 172 */
    private long F(long x, long y, long z) {
        return x & y | not(x) & z;
    }


    /* 176 */
    private long G(long x, long y, long z) {
        return x & z | y & not(z);
    }


    /* 180 */
    private long H(long x, long y, long z) {
        return x ^ y ^ z;
    }


    /* 184 */
    private long I(long x, long y, long z) {
        return y ^ (x | not(z));
    }


    /* 188 */
    private long ROTATE_LEFT(long x, long n) {
        return shl(x, (int) n) | shr(x, (int) (32L - n));
    }


    private long FF(long a, long b, long c, long d, long x, long s, long ac) {
        /* 193 */
        long tmp = plus(a, plus(plus(F(b, c, d), x), ac));
        /* 194 */
        tmp = ROTATE_LEFT(tmp, s);
        /* 195 */
        return plus(tmp, b);

    }


    private long GG(long a, long b, long c, long d, long x, long s, long ac) {
        /* 200 */
        long tmp = plus(a, plus(plus(G(b, c, d), x), ac));
        /* 201 */
        tmp = ROTATE_LEFT(tmp, s);
        /* 202 */
        return plus(tmp, b);

    }


    private long HH(long a, long b, long c, long d, long x, long s, long ac) {
        /* 207 */
        long tmp = plus(a, plus(plus(H(b, c, d), x), ac));
        /* 208 */
        tmp = ROTATE_LEFT(tmp, s);
        /* 209 */
        return plus(tmp, b);

    }


    private long II(long a, long b, long c, long d, long x, long s, long ac) {
        /* 214 */
        long tmp = plus(a, plus(plus(I(b, c, d), x), ac));
        /* 215 */
        tmp = ROTATE_LEFT(tmp, s);
        /* 216 */
        return plus(tmp, b);

    }


    private void decode(long[] output, int[] input, int in_from, int len) {
        /* 223 */
        for (int i = 0, j = 0; j < len; i++, j += 4) {
            /* 224 */
            output[i] = input[j + in_from] | shl(input[j + in_from + 1], 8) |
                    /* 225 */         shl(input[j + in_from + 2], 16) |
                    /* 226 */         shl(input[j + in_from + 3], 24);

        }

    }


    private void transform(int[] block, int from) {
        /* 231 */
        long a = this.state[0];
        /* 232 */
        long b = this.state[1];
        /* 233 */
        long c = this.state[2];
        /* 234 */
        long d = this.state[3];
        /* 235 */
        long[] x = new long[16];

        /* 237 */
        decode(x, block, from, 64);

        /* 239 */
        a = FF(a, b, c, d, x[0], 7L, 3614090360L);
        /* 240 */
        d = FF(d, a, b, c, x[1], 12L, 3905402710L);
        /* 241 */
        c = FF(c, d, a, b, x[2], 17L, 606105819L);
        /* 242 */
        b = FF(b, c, d, a, x[3], 22L, 3250441966L);
        /* 243 */
        a = FF(a, b, c, d, x[4], 7L, 4118548399L);
        /* 244 */
        d = FF(d, a, b, c, x[5], 12L, 1200080426L);
        /* 245 */
        c = FF(c, d, a, b, x[6], 17L, 2821735955L);
        /* 246 */
        b = FF(b, c, d, a, x[7], 22L, 4249261313L);
        /* 247 */
        a = FF(a, b, c, d, x[8], 7L, 1770035416L);
        /* 248 */
        d = FF(d, a, b, c, x[9], 12L, 2336552879L);
        /* 249 */
        c = FF(c, d, a, b, x[10], 17L, 4294925233L);
        /* 250 */
        b = FF(b, c, d, a, x[11], 22L, 2304563134L);
        /* 251 */
        a = FF(a, b, c, d, x[12], 7L, 1804603682L);
        /* 252 */
        d = FF(d, a, b, c, x[13], 12L, 4254626195L);
        /* 253 */
        c = FF(c, d, a, b, x[14], 17L, 2792965006L);
        /* 254 */
        b = FF(b, c, d, a, x[15], 22L, 1236535329L);


        /* 257 */
        a = GG(a, b, c, d, x[1], 5L, 4129170786L);
        /* 258 */
        d = GG(d, a, b, c, x[6], 9L, 3225465664L);
        /* 259 */
        c = GG(c, d, a, b, x[11], 14L, 643717713L);
        /* 260 */
        b = GG(b, c, d, a, x[0], 20L, 3921069994L);
        /* 261 */
        a = GG(a, b, c, d, x[5], 5L, 3593408605L);
        /* 262 */
        d = GG(d, a, b, c, x[10], 9L, 38016083L);
        /* 263 */
        c = GG(c, d, a, b, x[15], 14L, 3634488961L);
        /* 264 */
        b = GG(b, c, d, a, x[4], 20L, 3889429448L);
        /* 265 */
        a = GG(a, b, c, d, x[9], 5L, 568446438L);
        /* 266 */
        d = GG(d, a, b, c, x[14], 9L, 3275163606L);
        /* 267 */
        c = GG(c, d, a, b, x[3], 14L, 4107603335L);
        /* 268 */
        b = GG(b, c, d, a, x[8], 20L, 1163531501L);
        /* 269 */
        a = GG(a, b, c, d, x[13], 5L, 2850285829L);
        /* 270 */
        d = GG(d, a, b, c, x[2], 9L, 4243563512L);
        /* 271 */
        c = GG(c, d, a, b, x[7], 14L, 1735328473L);
        /* 272 */
        b = GG(b, c, d, a, x[12], 20L, 2368359562L);


        /* 275 */
        a = HH(a, b, c, d, x[5], 4L, 4294588738L);
        /* 276 */
        d = HH(d, a, b, c, x[8], 11L, 2272392833L);
        /* 277 */
        c = HH(c, d, a, b, x[11], 16L, 1839030562L);
        /* 278 */
        b = HH(b, c, d, a, x[14], 23L, 4259657740L);
        /* 279 */
        a = HH(a, b, c, d, x[1], 4L, 2763975236L);
        /* 280 */
        d = HH(d, a, b, c, x[4], 11L, 1272893353L);
        /* 281 */
        c = HH(c, d, a, b, x[7], 16L, 4139469664L);
        /* 282 */
        b = HH(b, c, d, a, x[10], 23L, 3200236656L);
        /* 283 */
        a = HH(a, b, c, d, x[13], 4L, 681279174L);
        /* 284 */
        d = HH(d, a, b, c, x[0], 11L, 3936430074L);
        /* 285 */
        c = HH(c, d, a, b, x[3], 16L, 3572445317L);
        /* 286 */
        b = HH(b, c, d, a, x[6], 23L, 76029189L);
        /* 287 */
        a = HH(a, b, c, d, x[9], 4L, 3654602809L);
        /* 288 */
        d = HH(d, a, b, c, x[12], 11L, 3873151461L);
        /* 289 */
        c = HH(c, d, a, b, x[15], 16L, 530742520L);
        /* 290 */
        b = HH(b, c, d, a, x[2], 23L, 3299628645L);


        /* 293 */
        a = II(a, b, c, d, x[0], 6L, 4096336452L);
        /* 294 */
        d = II(d, a, b, c, x[7], 10L, 1126891415L);
        /* 295 */
        c = II(c, d, a, b, x[14], 15L, 2878612391L);
        /* 296 */
        b = II(b, c, d, a, x[5], 21L, 4237533241L);
        /* 297 */
        a = II(a, b, c, d, x[12], 6L, 1700485571L);
        /* 298 */
        d = II(d, a, b, c, x[3], 10L, 2399980690L);
        /* 299 */
        c = II(c, d, a, b, x[10], 15L, 4293915773L);
        /* 300 */
        b = II(b, c, d, a, x[1], 21L, 2240044497L);
        /* 301 */
        a = II(a, b, c, d, x[8], 6L, 1873313359L);
        /* 302 */
        d = II(d, a, b, c, x[15], 10L, 4264355552L);
        /* 303 */
        c = II(c, d, a, b, x[6], 15L, 2734768916L);
        /* 304 */
        b = II(b, c, d, a, x[13], 21L, 1309151649L);
        /* 305 */
        a = II(a, b, c, d, x[4], 6L, 4149444226L);
        /* 306 */
        d = II(d, a, b, c, x[11], 10L, 3174756917L);
        /* 307 */
        c = II(c, d, a, b, x[2], 15L, 718787259L);
        /* 308 */
        b = II(b, c, d, a, x[9], 21L, 3951481745L);

        /* 310 */
        this.state[0] = plus(this.state[0], a);
        /* 311 */
        this.state[1] = plus(this.state[1], b);
        /* 312 */
        this.state[2] = plus(this.state[2], c);
        /* 313 */
        this.state[3] = plus(this.state[3], d);

    }


    /* 317 */
    public void update(int[] bytes) {
        do_update(clean_bytes(bytes));
    }


    /* 321 */
    public void update(String s) {
        do_update(to_bytes(s));
    }


    private int[] encode(long[] input, int len) {
        /* 325 */
        int[] output = new int[len];

        /* 327 */
        for (int i = 0, j = 0; j < len; i++, j += 4) {
            /* 328 */
            output[j] = (int) (input[i] & 0xFFL);
            /* 329 */
            output[j + 1] = (int) (input[i] >>> 8L & 0xFFL);
            /* 330 */
            output[j + 2] = (int) (input[i] >>> 16L & 0xFFL);
            /* 331 */
            output[j + 3] = (int) (input[i] >>> 24L & 0xFFL);

        }
        /* 333 */
        return output;

    }


    public int[] final_bytes() {
        /* 337 */
        int[] bits = encode(this.count, 8);




        /* 342 */
        int index = (int) (this.count[0] >>> 3L & 0x3FL);
        /* 343 */
        int padlen = (index < 56) ? (56 - index) : (120 - index);

        /* 345 */
        int[] padding = new int[padlen];
        /* 346 */
        padding[0] = 128;
        /* 347 */
        for (int i = 1; i < padlen; i++) {
            /* 348 */
            padding[i] = 0;

        }

        /* 351 */
        do_update(padding);

        /* 353 */
        do_update(bits);

        /* 355 */
        int[] digest = encode(this.state, 16);

        /* 357 */
        return digest;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpMD5.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */