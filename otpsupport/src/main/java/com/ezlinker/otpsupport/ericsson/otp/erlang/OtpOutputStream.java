/*      */
package com.ezlinker.otpsupport.ericsson.otp.erlang;
/*      */
/*      */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */ public class OtpOutputStream
        /*      */ extends ByteArrayOutputStream
        /*      */ {
    /*      */   public static final int defaultInitialSize = 2048;
    /*      */   public static final int defaultIncrement = 2048;
    /*   53 */   private static final DecimalFormat eform = new DecimalFormat("e+00;e-00");
    /*      */
    /*   55 */   private static final BigDecimal ten = new BigDecimal(10.0D);
    /*      */
    /*   57 */   private static final BigDecimal one = new BigDecimal(1.0D);
    /*      */
    /*   59 */   private int fixedSize = Integer.MAX_VALUE;

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*   65 */
    public OtpOutputStream() {
        this(2048);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*   72 */
    public OtpOutputStream(int size) {
        super(size);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public OtpOutputStream(OtpErlangObject o) {
        /*   79 */
        this();
        /*   80 */
        write_any(o);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*   96 */   OtpInputStream getOtpInputStream(int offset) {
        return new OtpInputStream(this.buf, offset, this.count - offset, 0);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  105 */
    public int getPos() {
        return this.count;
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  114 */
    public void trimToSize() {
        resize(this.count);
    }

    /*      */
    /*      */
    /*      */
    private void resize(int size) {
        /*  118 */
        if (size < this.buf.length) {
            /*  119 */
            byte[] tmp = new byte[size];
            /*  120 */
            System.arraycopy(this.buf, 0, tmp, 0, size);
            /*  121 */
            this.buf = tmp;
            /*  122 */
        } else if (size > this.buf.length) {
            /*  123 */
            ensureCapacity(size);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void ensureCapacity(int minCapacity) {
        /*  136 */
        if (minCapacity > this.fixedSize) {
            /*  137 */
            throw new IllegalArgumentException("Trying to increase fixed-size buffer");
            /*      */
        }
        /*      */
        /*  140 */
        int oldCapacity = this.buf.length;
        /*  141 */
        if (minCapacity > oldCapacity) {
            /*  142 */
            int newCapacity = oldCapacity * 3 / 2 + 1;
            /*  143 */
            if (newCapacity < oldCapacity + 2048) {
                /*  144 */
                newCapacity = oldCapacity + 2048;
                /*      */
            }
            /*  146 */
            if (newCapacity < minCapacity) {
                /*  147 */
                newCapacity = minCapacity;
                /*      */
            }
            /*  149 */
            newCapacity = Math.min(this.fixedSize, newCapacity);
            /*      */
            /*  151 */
            byte[] tmp = new byte[newCapacity];
            /*  152 */
            System.arraycopy(this.buf, 0, tmp, 0, this.count);
            /*  153 */
            this.buf = tmp;
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write(byte b) {
        /*  165 */
        ensureCapacity(this.count + 1);
        /*  166 */
        this.buf[this.count++] = b;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  177 */
    public void write(byte[] abuf) {
        write(abuf, 0, abuf.length);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public synchronized void write(int b) {
        /*  187 */
        ensureCapacity(this.count + 1);
        /*  188 */
        this.buf[this.count] = (byte) b;
        /*  189 */
        this.count++;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public synchronized void write(byte[] b, int off, int len) {
        /*  199 */
        if (off < 0 || off > b.length || len < 0 || off + len - b.length > 0) {
            /*  200 */
            throw new IndexOutOfBoundsException();
            /*      */
        }
        /*  202 */
        ensureCapacity(this.count + len);
        /*  203 */
        System.arraycopy(b, off, this.buf, this.count, len);
        /*  204 */
        this.count += len;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*  209 */
    public synchronized void writeTo(OutputStream out) throws IOException {
        super.writeTo(out);
    }

    /*      */
    /*      */
    /*      */
    public synchronized void writeToAndFlush(OutputStream out) throws IOException {
        /*  213 */
        super.writeTo(out);
        /*  214 */
        out.flush();
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  225 */
    public void write1(long n) {
        write((byte) (int) (n & 0xFFL));
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  236 */
    public void writeN(byte[] bytes) {
        write(bytes);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  247 */
    public int length() {
        return this.buf.length;
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    @Deprecated
    /*  261 */ public int count() {
        return this.count;
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write2BE(long n) {
        /*  271 */
        write((byte) (int) ((n & 0xFF00L) >> 8L));
        /*  272 */
        write((byte) (int) (n & 0xFFL));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write4BE(long n) {
        /*  282 */
        write((byte) (int) ((n & 0xFFFFFFFFFF000000L) >> 24L));
        /*  283 */
        write((byte) (int) ((n & 0xFF0000L) >> 16L));
        /*  284 */
        write((byte) (int) ((n & 0xFF00L) >> 8L));
        /*  285 */
        write((byte) (int) (n & 0xFFL));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write8BE(long n) {
        /*  296 */
        write((byte) (int) (n >> 56L & 0xFFL));
        /*  297 */
        write((byte) (int) (n >> 48L & 0xFFL));
        /*  298 */
        write((byte) (int) (n >> 40L & 0xFFL));
        /*  299 */
        write((byte) (int) (n >> 32L & 0xFFL));
        /*  300 */
        write((byte) (int) (n >> 24L & 0xFFL));
        /*  301 */
        write((byte) (int) (n >> 16L & 0xFFL));
        /*  302 */
        write((byte) (int) (n >> 8L & 0xFFL));
        /*  303 */
        write((byte) (int) (n & 0xFFL));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void writeLE(long n, int b) {
        /*  315 */
        long v = n;
        /*  316 */
        for (int i = 0; i < b; i++) {
            /*  317 */
            write((byte) (int) (v & 0xFFL));
            /*  318 */
            v >>= 8L;
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write2LE(long n) {
        /*  329 */
        write((byte) (int) (n & 0xFFL));
        /*  330 */
        write((byte) (int) ((n & 0xFF00L) >> 8L));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write4LE(long n) {
        /*  340 */
        write((byte) (int) (n & 0xFFL));
        /*  341 */
        write((byte) (int) ((n & 0xFF00L) >> 8L));
        /*  342 */
        write((byte) (int) ((n & 0xFF0000L) >> 16L));
        /*  343 */
        write((byte) (int) ((n & 0xFFFFFFFFFF000000L) >> 24L));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write8LE(long n) {
        /*  354 */
        write((byte) (int) (n & 0xFFL));
        /*  355 */
        write((byte) (int) (n >> 8L & 0xFFL));
        /*  356 */
        write((byte) (int) (n >> 16L & 0xFFL));
        /*  357 */
        write((byte) (int) (n >> 24L & 0xFFL));
        /*  358 */
        write((byte) (int) (n >> 32L & 0xFFL));
        /*  359 */
        write((byte) (int) (n >> 40L & 0xFFL));
        /*  360 */
        write((byte) (int) (n >> 48L & 0xFFL));
        /*  361 */
        write((byte) (int) (n >> 56L & 0xFFL));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void poke4BE(int offset, long n) {
        /*  389 */
        if (offset < this.count) {
            /*  390 */
            this.buf[offset + 0] = (byte) (int) ((n & 0xFFFFFFFFFF000000L) >> 24L);
            /*  391 */
            this.buf[offset + 1] = (byte) (int) ((n & 0xFF0000L) >> 16L);
            /*  392 */
            this.buf[offset + 2] = (byte) (int) ((n & 0xFF00L) >> 8L);
            /*  393 */
            this.buf[offset + 3] = (byte) (int) (n & 0xFFL);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_atom(String atom) {
        /*      */
        String enc_atom;
        /*  406 */
        boolean isLatin1 = true;
        /*      */
        /*  408 */
        if (atom.codePointCount(0, atom.length()) <= 255) {
            /*  409 */
            enc_atom = atom;
            /*      */
            /*      */
        }
        /*      */
        else {
            /*      */
            /*      */
            /*  415 */
            enc_atom = new String(OtpErlangString.stringToCodePoints(atom), 0, 255);
            /*      */
        }
        /*      */
        /*      */
        int offset;
        /*  419 */
        for (offset = 0; offset < enc_atom.length(); ) {
            /*  420 */
            int cp = enc_atom.codePointAt(offset);
            /*  421 */
            if ((cp & 0xFFFFFF00) != 0) {
                /*  422 */
                isLatin1 = false;
                /*      */
                break;
                /*      */
            }
            /*  425 */
            offset += Character.charCount(cp);
            /*      */
        }
        try {
            /*      */
            byte[] bytes;
            /*  428 */
            if (isLatin1) {
                /*  429 */
                bytes = enc_atom.getBytes("ISO-8859-1");
                /*  430 */
                write1(100L);
                /*  431 */
                write2BE(bytes.length);
                /*      */
            } else {
                /*  433 */
                bytes = enc_atom.getBytes("UTF-8");
                /*  434 */
                int length = bytes.length;
                /*  435 */
                if (length < 256) {
                    /*  436 */
                    write1(119L);
                    /*  437 */
                    write1(length);
                    /*      */
                } else {
                    /*  439 */
                    write1(118L);
                    /*  440 */
                    write2BE(length);
                    /*      */
                }
                /*      */
            }
            /*  443 */
            writeN(bytes);
            /*  444 */
        } catch (UnsupportedEncodingException e) {
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*      */
            /*  451 */
            write1(119L);
            /*  452 */
            write1(2L);
            /*  453 */
            write2BE(65535L);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_binary(byte[] bin) {
        /*  464 */
        write1(109L);
        /*  465 */
        write4BE(bin.length);
        /*  466 */
        writeN(bin);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_bitstr(byte[] bin, int pad_bits) {
        /*  478 */
        if (pad_bits == 0) {
            /*  479 */
            write_binary(bin);
            /*      */
            return;
            /*      */
        }
        /*  482 */
        write1(77L);
        /*  483 */
        write4BE(bin.length);
        /*  484 */
        write1((8 - pad_bits));
        /*  485 */
        writeN(bin);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  495 */
    public void write_boolean(boolean b) {
        write_atom(String.valueOf(b));
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  506 */
    public void write_byte(byte b) {
        write_long(b & 0xFFL, true);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  518 */
    public void write_char(char c) {
        write_long(c & 0xFFFFL, true);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_double(double d) {
        /*  528 */
        write1(70L);
        /*  529 */
        write8BE(Double.doubleToLongBits(d));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  539 */
    public void write_float(float f) {
        write_double(f);
    }

    /*      */
    /*      */
    /*      */
    public void write_big_integer(BigInteger v) {
        /*  543 */
        if (v.bitLength() < 64) {
            /*  544 */
            write_long(v.longValue(), true);
            /*      */
            return;
            /*      */
        }
        /*  547 */
        int signum = v.signum();
        /*  548 */
        BigInteger val = v;
        /*  549 */
        if (signum < 0) {
            /*  550 */
            val = val.negate();
            /*      */
        }
        /*  552 */
        byte[] magnitude = val.toByteArray();
        /*  553 */
        int n = magnitude.length;
        /*      */
        /*  555 */
        for (int i = 0, j = n; i < j--; i++) {
            /*      */
            /*  557 */
            byte b = magnitude[i];
            /*  558 */
            magnitude[i] = magnitude[j];
            /*  559 */
            magnitude[j] = b;
            /*      */
        }
        /*  561 */
        if ((n & 0xFF) == n) {
            /*  562 */
            write1(110L);
            /*  563 */
            write1(n);
            /*      */
        } else {
            /*  565 */
            write1(111L);
            /*  566 */
            write4BE(n);
            /*      */
        }
        /*  568 */
        write1((signum < 0) ? 1L : 0L);
        /*      */
        /*  570 */
        writeN(magnitude);
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */   void write_long(long v, boolean unsigned) {
        /*  579 */
        if ((v & 0xFFL) == v) {
            /*      */
            /*  581 */
            write1(97L);
            /*  582 */
            write1(v);
            /*      */
            /*      */
        }
        /*  585 */
        else if ((v < 0L && unsigned) || v < -134217728L || v > 134217727L) {
            /*      */
            /*      */
            /*  588 */
            long abs = unsigned ? v : ((v < 0L) ? -v : v);
            /*  589 */
            int sign = unsigned ? 0 : ((v < 0L) ? 1 : 0);
            /*      */
            int n;
            /*      */
            long mask;
            /*  592 */
            for (mask = 4294967295L, n = 4; (abs & mask) != abs; ) {
                n++;
                mask = mask << 8L | 0xFFL;
            }
            /*      */
            /*      */
            /*  595 */
            write1(110L);
            /*  596 */
            write1(n);
            /*  597 */
            write1(sign);
            /*  598 */
            writeLE(abs, n);
            /*      */
        } else {
            /*  600 */
            write1(98L);
            /*  601 */
            write4BE(v);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  613 */
    public void write_long(long l) {
        write_long(l, false);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  624 */
    public void write_ulong(long ul) {
        write_long(ul, true);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  634 */
    public void write_int(int i) {
        write_long(i, false);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  645 */
    public void write_uint(int ui) {
        write_long(ui & 0xFFFFFFFFL, true);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  655 */
    public void write_short(short s) {
        write_long(s, false);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  666 */
    public void write_ushort(short us) {
        write_long(us & 0xFFFFL, true);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_list_head(int arity) {
        /*  678 */
        if (arity == 0) {
            /*  679 */
            write_nil();
            /*      */
        } else {
            /*  681 */
            write1(108L);
            /*  682 */
            write4BE(arity);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  690 */
    public void write_nil() {
        write1(106L);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_tuple_head(int arity) {
        /*  702 */
        if (arity < 255) {
            /*  703 */
            write1(104L);
            /*  704 */
            write1(arity);
            /*      */
        } else {
            /*  706 */
            write1(105L);
            /*  707 */
            write4BE(arity);
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_pid(String node, int id, int serial, int creation) {
        /*  731 */
        write1(103L);
        /*  732 */
        write_atom(node);
        /*  733 */
        write4BE((id & 0x7FFF));
        /*  734 */
        write4BE((serial & 0x1FFF));
        /*  735 */
        write1((creation & 0x3));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_port(String node, int id, int creation) {
        /*  753 */
        write1(102L);
        /*  754 */
        write_atom(node);
        /*  755 */
        write4BE((id & 0xFFFFFFF));
        /*  756 */
        write1((creation & 0x3));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_ref(String node, int id, int creation) {
        /*  774 */
        write1(101L);
        /*  775 */
        write_atom(node);
        /*  776 */
        write4BE((id & 0x3FFFF));
        /*  777 */
        write1((creation & 0x3));
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_ref(String node, int[] ids, int creation) {
        /*  798 */
        int arity = ids.length;
        /*  799 */
        if (arity > 3) {
            /*  800 */
            arity = 3;
            /*      */
        }
        /*      */
        /*  803 */
        if (arity == 1) {
            /*      */
            /*  805 */
            write_ref(node, ids[0], creation);
            /*      */
        } else {
            /*      */
            /*  808 */
            write1(114L);
            /*      */
            /*      */
            /*  811 */
            write2BE(arity);
            /*      */
            /*  813 */
            write_atom(node);
            /*      */
            /*      */
            /*  816 */
            write1((creation & 0x3));
            /*      */
            /*      */
            /*  819 */
            write4BE((ids[0] & 0x3FFFF));
            /*      */
            /*      */
            /*  822 */
            for (int i = 1; i < arity; i++) {
                /*  823 */
                write4BE(ids[i]);
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_string(String s) {
        /*  835 */
        int len = s.length();
        /*      */
        /*  837 */
        switch (len) {
            /*      */
            case 0:
                /*  839 */
                write_nil();
                /*      */
                return;
            /*      */
        }
        /*  842 */
        if (len <= 65535 && is8bitString(s)) {
            /*      */
            try {
                /*  844 */
                byte[] bytebuf = s.getBytes("ISO-8859-1");
                /*  845 */
                write1(107L);
                /*  846 */
                write2BE(len);
                /*  847 */
                writeN(bytebuf);
                /*  848 */
            } catch (UnsupportedEncodingException e) {
                /*  849 */
                write_nil();
                /*      */
            }
            /*      */
        } else {
            /*  852 */
            int[] codePoints = OtpErlangString.stringToCodePoints(s);
            /*  853 */
            write_list_head(codePoints.length);
            /*  854 */
            for (int codePoint : codePoints) {
                /*  855 */
                write_int(codePoint);
                /*      */
            }
            /*  857 */
            write_nil();
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    private boolean is8bitString(String s) {
        /*  863 */
        for (int i = 0; i < s.length(); i++) {
            /*  864 */
            char c = s.charAt(i);
            /*  865 */
            if (c < '\000' || c > 'ÿ') {
                /*  866 */
                return false;
                /*      */
            }
            /*      */
        }
        /*  869 */
        return true;
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  879 */
    public void write_compressed(OtpErlangObject o) {
        write_compressed(o, -1);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_compressed(OtpErlangObject o, int level) {
        /*  892 */
        OtpOutputStream oos = new OtpOutputStream(o);
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*      */
        /*  899 */
        if (oos.size() < 5) {
            /*      */
            /*      */
            try {
                /*  902 */
                oos.writeToAndFlush(this);
                /*      */
                /*      */
                /*  905 */
                close();
                /*  906 */
            } catch (IOException e) {
                /*  907 */
                throw new IllegalArgumentException("Intermediate stream failed for Erlang object " + o);
                /*      */
            }
            /*      */
        } else {
            /*      */
            /*  911 */
            int startCount = this.count;
            /*      */
            /*      */
            /*  914 */
            int destCount = startCount + oos.size();
            /*  915 */
            this.fixedSize = destCount;
            /*  916 */
            Deflater def = new Deflater(level);
            /*  917 */
            DeflaterOutputStream dos = new DeflaterOutputStream(this, def);
            /*      */
            /*      */
            try {
                /*  920 */
                write1(80L);
                /*  921 */
                write4BE(oos.size());
                /*  922 */
                oos.writeTo(dos);
                /*  923 */
                dos.close();
                /*  924 */
            } catch (IllegalArgumentException e) {
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*      */
                /*  941 */
                def.end();
                /*      */
                /*      */
                /*  944 */
                this.count = startCount;
                /*      */
                try {
                    /*  946 */
                    oos.writeTo(this);
                    /*      */
                    /*      */
                    /*  949 */
                    close();
                    /*  950 */
                } catch (IOException e2) {
                    /*  951 */
                    throw new IllegalArgumentException("Intermediate stream failed for Erlang object " + o);
                    /*      */
                }
                /*      */
                /*  954 */
            } catch (IOException e) {
                /*  955 */
                throw new IllegalArgumentException("Intermediate stream failed for Erlang object " + o);
                /*      */
            } finally {
                /*      */
                /*  958 */
                this.fixedSize = Integer.MAX_VALUE;
                /*      */
            }
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    /*  971 */
    public void write_any(OtpErlangObject o) {
        o.encode(this);
    }

    /*      */
    /*      */
    /*      */
    /*      */
    /*      */
    public void write_fun(OtpErlangPid pid, String module, long old_index, int arity, byte[] md5, long index, long uniq, OtpErlangObject[] freeVars) {
        /*  977 */
        if (arity == -1) {
            /*  978 */
            write1(117L);
            /*  979 */
            write4BE(freeVars.length);
            /*  980 */
            pid.encode(this);
            /*  981 */
            write_atom(module);
            /*  982 */
            write_long(index);
            /*  983 */
            write_long(uniq);
            /*  984 */
            for (OtpErlangObject fv : freeVars) {
                /*  985 */
                fv.encode(this);
                /*      */
            }
            /*      */
        } else {
            /*  988 */
            write1(112L);
            /*  989 */
            int saveSizePos = getPos();
            /*  990 */
            write4BE(0L);
            /*  991 */
            write1(arity);
            /*  992 */
            writeN(md5);
            /*  993 */
            write4BE(index);
            /*  994 */
            write4BE(freeVars.length);
            /*  995 */
            write_atom(module);
            /*  996 */
            write_long(old_index);
            /*  997 */
            write_long(uniq);
            /*  998 */
            pid.encode(this);
            /*  999 */
            for (OtpErlangObject fv : freeVars) {
                /* 1000 */
                fv.encode(this);
                /*      */
            }
            /* 1002 */
            poke4BE(saveSizePos, (getPos() - saveSizePos));
            /*      */
        }
        /*      */
    }

    /*      */
    /*      */
    /*      */
    public void write_external_fun(String module, String function, int arity) {
        /* 1008 */
        write1(113L);
        /* 1009 */
        write_atom(module);
        /* 1010 */
        write_atom(function);
        /* 1011 */
        write_long(arity);
        /*      */
    }

    /*      */
    /*      */
    public void write_map_head(int arity) {
        /* 1015 */
        write1(116L);
        /* 1016 */
        write4BE(arity);
        /*      */
    }
    /*      */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */