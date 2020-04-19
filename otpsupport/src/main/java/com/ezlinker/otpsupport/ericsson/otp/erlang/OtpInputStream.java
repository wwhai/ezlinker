//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ezlinker.otpsupport.ericsson.otp.erlang;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class OtpInputStream extends ByteArrayInputStream {
    public static int DECODE_INT_LISTS_AS_STRINGS = 1;
    private final int flags;

    public OtpInputStream(byte[] buf) {
        this(buf, 0);
    }

    public OtpInputStream(byte[] buf, int flags) {
        super(buf);
        this.flags = flags;
    }

    public OtpInputStream(byte[] buf, int offset, int length, int flags) {
        super(buf, offset, length);
        this.flags = flags;
    }

    public static long byte_array_to_long(byte[] b, boolean unsigned) throws OtpErlangDecodeException {
        long v;
        switch (b.length) {
            case 0:
                v = 0L;
                break;
            case 1:
            case 3:
            default:
                int i = 0;
                byte c = b[i];
                if (unsigned) {
                    if (c < 0) {
                        throw new OtpErlangDecodeException("Value not unsigned: " + Arrays.toString(b));
                    }

                    while (b[i] == 0) {
                        ++i;
                    }
                } else if (c == 0 || c == -1) {
                    for (i = 1; i < b.length && b[i] == c; ++i) {
                    }

                    if (i < b.length && ((c ^ b[i]) & 128) != 0) {
                        --i;
                    }
                }

                if (b.length - i > 8) {
                    throw new OtpErlangDecodeException("Value does not fit in long: " + Arrays.toString(b));
                }

                for (v = c < 0 ? -1L : 0L; i < b.length; ++i) {
                    v = v << 8 | (long) (b[i] & 255);
                }

                return v;
            case 2:
                v = (long) (((b[0] & 255) << 8) + (b[1] & 255));
                v = (long) ((short) ((int) v));
                if (v < 0L && unsigned) {
                    throw new OtpErlangDecodeException("Value not unsigned: " + v);
                }
                break;
            case 4:
                v = (long) (((b[0] & 255) << 24) + ((b[1] & 255) << 16) + ((b[2] & 255) << 8) + (b[3] & 255));
                v = (long) ((int) v);
                if (v < 0L && unsigned) {
                    throw new OtpErlangDecodeException("Value not unsigned: " + v);
                }
        }

        return v;
    }

    public int getPos() {
        return super.pos;
    }

    public int setPos(int pos) {
        int oldpos = super.pos;
        int apos = pos;
        if (pos > super.count) {
            apos = super.count;
        } else if (pos < 0) {
            apos = 0;
        }

        super.pos = apos;
        return oldpos;
    }

    public int readN(byte[] abuf) throws OtpErlangDecodeException {
        return this.readN(abuf, 0, abuf.length);
    }

    public int readN(byte[] abuf, int off, int len) throws OtpErlangDecodeException {
        if (len == 0 && this.available() == 0) {
            return 0;
        } else {
            int i = super.read(abuf, off, len);
            if (i < 0) {
                throw new OtpErlangDecodeException("Cannot read from input stream");
            } else {
                return i;
            }
        }
    }

    public int peek() throws OtpErlangDecodeException {
        return this.peek1();
    }

    public int peek1() throws OtpErlangDecodeException {
        try {
            int i = super.buf[super.pos];
            if (i < 0) {
                i += 256;
            }

            return i;
        } catch (Exception var3) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }
    }

    public int peek1skip_version() throws OtpErlangDecodeException {
        int i = this.peek1();
        if (i == 131) {
            this.read1();
            i = this.peek1();
        }

        return i;
    }

    public int read1() throws OtpErlangDecodeException {
        int i = super.read();
        if (i < 0) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        } else {
            return i;
        }
    }

    public int read1skip_version() throws OtpErlangDecodeException {
        int tag = this.read1();
        if (tag == 131) {
            tag = this.read1();
        }

        return tag;
    }

    public int read2BE() throws OtpErlangDecodeException {
        byte[] b = new byte[2];

        try {
            super.read(b);
        } catch (IOException var3) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        return (b[0] << 8 & '\uff00') + (b[1] & 255);
    }

    public int read4BE() throws OtpErlangDecodeException {
        byte[] b = new byte[4];

        try {
            super.read(b);
        } catch (IOException var3) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        return (b[0] << 24 & -16777216) + (b[1] << 16 & 16711680) + (b[2] << 8 & '\uff00') + (b[3] & 255);
    }

    public int read2LE() throws OtpErlangDecodeException {
        byte[] b = new byte[2];

        try {
            super.read(b);
        } catch (IOException var3) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        return (b[1] << 8 & '\uff00') + (b[0] & 255);
    }

    public int read4LE() throws OtpErlangDecodeException {
        byte[] b = new byte[4];

        try {
            super.read(b);
        } catch (IOException var3) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        return (b[3] << 24 & -16777216) + (b[2] << 16 & 16711680) + (b[1] << 8 & '\uff00') + (b[0] & 255);
    }

    public long readLE(int n) throws OtpErlangDecodeException {
        byte[] b = new byte[n];

        try {
            super.read(b);
        } catch (IOException var6) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        long v = 0L;

        for (int i = n; i-- > 0; v = v << 8 | (long) b[i] & 255L) {
        }

        return v;
    }

    public long readBE(int n) throws OtpErlangDecodeException {
        byte[] b = new byte[n];

        try {
            super.read(b);
        } catch (IOException var6) {
            throw new OtpErlangDecodeException("Cannot read from input stream");
        }

        long v = 0L;

        for (int i = 0; i < n; ++i) {
            v = v << 8 | (long) b[i] & 255L;
        }

        return v;
    }

    public boolean read_boolean() throws OtpErlangDecodeException {
        return Boolean.valueOf(this.read_atom());
    }

    public String read_atom() throws OtpErlangDecodeException {
        int len = -1;
        int tag = this.read1skip_version();
        byte[] strbuf;
        String atom;
        switch (tag) {
            case 100:
                len = this.read2BE();
                strbuf = new byte[len];
                this.readN(strbuf);

                try {
                    atom = new String(strbuf, "ISO-8859-1");
                } catch (UnsupportedEncodingException var7) {
                    throw new OtpErlangDecodeException("Failed to decode ISO-8859-1 atom");
                }

                if (atom.length() > 255) {
                    atom = atom.substring(0, 255);
                }
                break;
            case 119:
                len = this.read1();
            case 118:
                if (len < 0) {
                    len = this.read2BE();
                }

                strbuf = new byte[len];
                this.readN(strbuf);

                try {
                    atom = new String(strbuf, "UTF-8");
                } catch (UnsupportedEncodingException var6) {
                    throw new OtpErlangDecodeException("Failed to decode UTF-8 atom");
                }

                if (atom.codePointCount(0, atom.length()) > 255) {
                    int[] cps = OtpErlangString.stringToCodePoints(atom);
                    atom = new String(cps, 0, 255);
                }
                break;
            default:
                throw new OtpErlangDecodeException("wrong tag encountered, expected 100, or 118, got " + tag);
        }

        return atom;
    }

    public byte[] read_binary() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 109) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected 109, got " + tag);
        } else {
            int len = this.read4BE();
            byte[] bin = new byte[len];
            this.readN(bin);
            return bin;
        }
    }

    public byte[] read_bitstr(int[] pad_bits) throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 77) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected 77, got " + tag);
        } else {
            int len = this.read4BE();
            byte[] bin = new byte[len];
            int tail_bits = this.read1();
            if (tail_bits >= 0 && 7 >= tail_bits) {
                if (len == 0 && tail_bits != 0) {
                    throw new OtpErlangDecodeException("Length 0 on bitstr with tail bit count: " + tail_bits);
                } else {
                    this.readN(bin);
                    pad_bits[0] = 8 - tail_bits;
                    return bin;
                }
            } else {
                throw new OtpErlangDecodeException("Wrong tail bit count in bitstr: " + tail_bits);
            }
        }
    }

    public float read_float() throws OtpErlangDecodeException {
        double d = this.read_double();
        return (float) d;
    }

    public double read_double() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        switch (tag) {
            case 70:
                return Double.longBitsToDouble(this.readBE(8));
            case 99:
                byte[] strbuf = new byte[31];
                this.readN(strbuf);
                String str = OtpErlangString.newString(strbuf);
                int epos = str.indexOf(101, 0);
                if (epos < 0) {
                    throw new OtpErlangDecodeException("Invalid float format: '" + str + "'");
                }

                String estr = str.substring(epos + 1).trim();
                if (estr.substring(0, 1).equals("+")) {
                    estr = estr.substring(1);
                }

                int exp = Integer.valueOf(estr);
                BigDecimal val = (new BigDecimal(str.substring(0, epos))).movePointRight(exp);
                return val.doubleValue();
            default:
                throw new OtpErlangDecodeException("Wrong tag encountered, expected 70, got " + tag);
        }
    }

    public byte read_byte() throws OtpErlangDecodeException {
        long l = this.read_long(false);
        byte i = (byte) ((int) l);
        if (l != (long) i) {
            throw new OtpErlangDecodeException("Value does not fit in byte: " + l);
        } else {
            return i;
        }
    }

    public char read_char() throws OtpErlangDecodeException {
        long l = this.read_long(true);
        char i = (char) ((int) l);
        if (l != ((long) i & 65535L)) {
            throw new OtpErlangDecodeException("Value does not fit in char: " + l);
        } else {
            return i;
        }
    }

    public int read_uint() throws OtpErlangDecodeException {
        long l = this.read_long(true);
        int i = (int) l;
        if (l != ((long) i & 4294967295L)) {
            throw new OtpErlangDecodeException("Value does not fit in uint: " + l);
        } else {
            return i;
        }
    }

    public int read_int() throws OtpErlangDecodeException {
        long l = this.read_long(false);
        int i = (int) l;
        if (l != (long) i) {
            throw new OtpErlangDecodeException("Value does not fit in int: " + l);
        } else {
            return i;
        }
    }

    public short read_ushort() throws OtpErlangDecodeException {
        long l = this.read_long(true);
        short i = (short) ((int) l);
        if (l != ((long) i & 65535L)) {
            throw new OtpErlangDecodeException("Value does not fit in ushort: " + l);
        } else {
            return i;
        }
    }

    public short read_short() throws OtpErlangDecodeException {
        long l = this.read_long(false);
        short i = (short) ((int) l);
        if (l != (long) i) {
            throw new OtpErlangDecodeException("Value does not fit in short: " + l);
        } else {
            return i;
        }
    }

    public long read_ulong() throws OtpErlangDecodeException {
        return this.read_long(true);
    }

    public long read_long() throws OtpErlangDecodeException {
        return this.read_long(false);
    }

    public long read_long(boolean unsigned) throws OtpErlangDecodeException {
        byte[] b = this.read_integer_byte_array();
        return byte_array_to_long(b, unsigned);
    }

    public byte[] read_integer_byte_array() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        byte[] nb;
        switch (tag) {
            case 97:
                nb = new byte[]{0, (byte) this.read1()};
                break;
            case 98:
                nb = new byte[4];
                if (this.readN(nb) != 4) {
                    throw new OtpErlangDecodeException("Cannot read from intput stream");
                }
                break;
            case 110:
            case 111:
                int arity;
                int sign;
                if (tag == 110) {
                    arity = this.read1();
                    sign = this.read1();
                } else {
                    arity = this.read4BE();
                    sign = this.read1();
                    if (arity + 1 < 0) {
                        throw new OtpErlangDecodeException("Value of largeBig does not fit in BigInteger, arity " + arity + " sign " + sign);
                    }
                }

                nb = new byte[arity + 1];
                if (this.readN(nb, 0, arity) != arity) {
                    throw new OtpErlangDecodeException("Cannot read from intput stream");
                }

                int c = 0;

                int j;
                for (j = nb.length; c < j--; ++c) {
                    byte b = nb[c];
                    nb[c] = nb[j];
                    nb[j] = b;
                }

                if (sign != 0) {
                    c = 1;

                    for (j = nb.length; j-- > 0; c >>= 8) {
                        c += ~nb[j] & 255;
                        nb[j] = (byte) c;
                    }
                }
                break;
            default:
                throw new OtpErlangDecodeException("Not valid integer tag: " + tag);
        }

        return nb;
    }

    public int read_list_head() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        int arity;
        switch (tag) {
            case 106:
                arity = 0;
                break;
            case 107:
                arity = this.read2BE();
                break;
            case 108:
                arity = this.read4BE();
                break;
            default:
                throw new OtpErlangDecodeException("Not valid list tag: " + tag);
        }

        return arity;
    }

    public int read_tuple_head() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        int arity;
        switch (tag) {
            case 104:
                arity = this.read1();
                break;
            case 105:
                arity = this.read4BE();
                break;
            default:
                throw new OtpErlangDecodeException("Not valid tuple tag: " + tag);
        }

        return arity;
    }

    public int read_nil() throws OtpErlangDecodeException {
        int arity;
        int tag = this.read1skip_version();
        switch (tag) {
            case 106:
                arity = 0;
                return arity;
            default:
                throw new OtpErlangDecodeException("Not valid nil tag: " + tag);
        }
    }

    public OtpErlangPid read_pid() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 103) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected 103, got " + tag);
        } else {
            String node = this.read_atom();
            int id = this.read4BE() & 32767;
            int serial = this.read4BE() & 8191;
            int creation = this.read1() & 3;
            return new OtpErlangPid(node, id, serial, creation);
        }
    }

    public OtpErlangPort read_port() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 102) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected 102, got " + tag);
        } else {
            String node = this.read_atom();
            int id = this.read4BE() & 268435455;
            int creation = this.read1() & 3;
            return new OtpErlangPort(node, id, creation);
        }
    }

    public OtpErlangRef read_ref() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        String node;
        int creation;
        switch (tag) {
            case 101:
                node = this.read_atom();
                int id = this.read4BE() & 262143;
                creation = this.read1() & 3;
                return new OtpErlangRef(node, id, creation);
            case 114:
                int arity = this.read2BE();
                node = this.read_atom();
                creation = this.read1() & 3;
                int[] ids = new int[arity];

                for (int i = 0; i < arity; ++i) {
                    ids[i] = this.read4BE();
                }

                ids[0] &= 262143;
                return new OtpErlangRef(node, ids, creation);
            default:
                throw new OtpErlangDecodeException("Wrong tag encountered, expected ref, got " + tag);
        }
    }

    public OtpErlangFun read_fun() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        int arity;
        long oldIndex;
        if (tag == 117) {
            arity = this.read4BE();
            OtpErlangPid pid = this.read_pid();
            String module = this.read_atom();
            long index = this.read_long();
            oldIndex = this.read_long();
            OtpErlangObject[] freeVars = new OtpErlangObject[arity];

            for (int i = 0; i < arity; ++i) {
                freeVars[i] = this.read_any();
            }

            return new OtpErlangFun(pid, module, index, oldIndex, freeVars);
        } else if (tag != 112) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected fun, got " + tag);
        } else {
            this.read4BE();
            arity = this.read1();
            byte[] md5 = new byte[16];
            this.readN(md5);
            int index = this.read4BE();
            int nFreeVars = this.read4BE();
            String module = this.read_atom();
            oldIndex = this.read_long();
            long uniq = this.read_long();
            OtpErlangPid pid = this.read_pid();
            OtpErlangObject[] freeVars = new OtpErlangObject[nFreeVars];

            for (int i = 0; i < nFreeVars; ++i) {
                freeVars[i] = this.read_any();
            }

            return new OtpErlangFun(pid, module, arity, md5, index, oldIndex, uniq, freeVars);
        }
    }

    public OtpErlangExternalFun read_external_fun() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 113) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected external fun, got " + tag);
        } else {
            String module = this.read_atom();
            String function = this.read_atom();
            int arity = (int) this.read_long();
            return new OtpErlangExternalFun(module, function, arity);
        }
    }

    public String read_string() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        int len;
        switch (tag) {
            case 106:
                return "";
            case 107:
                len = this.read2BE();
                byte[] strbuf = new byte[len];
                this.readN(strbuf);
                return OtpErlangString.newString(strbuf);
            case 108:
                len = this.read4BE();
                int[] intbuf = new int[len];

                for (int i = 0; i < len; ++i) {
                    intbuf[i] = this.read_int();
                    if (!OtpErlangString.isValidCodePoint(intbuf[i])) {
                        throw new OtpErlangDecodeException("Invalid CodePoint: " + intbuf[i]);
                    }
                }

                this.read_nil();
                return new String(intbuf, 0, intbuf.length);
            default:
                throw new OtpErlangDecodeException("Wrong tag encountered, expected 107 or 108, got " + tag);
        }
    }

    public OtpErlangObject read_compressed() throws OtpErlangDecodeException {
        int tag = this.read1skip_version();
        if (tag != 80) {
            throw new OtpErlangDecodeException("Wrong tag encountered, expected 80, got " + tag);
        } else {
            int size = this.read4BE();
            byte[] abuf = new byte[size];
            InflaterInputStream is = new InflaterInputStream(this, new Inflater(), size);
            int curPos = 0;

            try {
                int curRead;
                while (curPos < size && (curRead = is.read(abuf, curPos, size - curPos)) != -1) {
                    curPos += curRead;
                }

                if (curPos != size) {
                    throw new OtpErlangDecodeException("Decompression gave " + curPos + " bytes, not " + size);
                }
            } catch (IOException var7) {
                throw new OtpErlangDecodeException("Cannot read from input stream");
            }

            OtpInputStream ois = new OtpInputStream(abuf, this.flags);
            return ois.read_any();
        }
    }

    public OtpErlangObject read_any() throws OtpErlangDecodeException {
        int tag = this.peek1skip_version();
        switch (tag) {
            case 70:
            case 99:
                return new OtpErlangDouble(this);
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 78:
            case 79:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 115:
            default:
                throw new OtpErlangDecodeException("Uknown data type: " + tag);
            case 77:
                return new OtpErlangBitstr(this);
            case 80:
                return this.read_compressed();
            case 97:
            case 98:
            case 110:
            case 111:
                return new OtpErlangLong(this);
            case 100:
            case 118:
            case 119:
                return new OtpErlangAtom(this);
            case 101:
            case 114:
                return new OtpErlangRef(this);
            case 102:
                return new OtpErlangPort(this);
            case 103:
                return new OtpErlangPid(this);
            case 104:
            case 105:
                return new OtpErlangTuple(this);
            case 106:
            case 108:
                if ((this.flags & DECODE_INT_LISTS_AS_STRINGS) != 0) {
                    int savePos = this.getPos();

                    try {
                        return new OtpErlangString(this);
                    } catch (OtpErlangDecodeException var4) {
                        this.setPos(savePos);
                    }
                }

                return new OtpErlangList(this);
            case 107:
                return new OtpErlangString(this);
            case 109:
                return new OtpErlangBinary(this);
            case 112:
            case 117:
                return new OtpErlangFun(this);
            case 113:
                return new OtpErlangExternalFun(this);
            case 116:
                return new OtpErlangMap(this);
        }
    }

    public int read_map_head() throws OtpErlangDecodeException {
        int arity;
        int tag = this.read1skip_version();
        switch (tag) {
            case 116:
                arity = this.read4BE();
                return arity;
            default:
                throw new OtpErlangDecodeException("Not valid map tag: " + tag);
        }
    }
}
