
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class AbstractNode
        implements OtpTransportFactory {

    static final int NTYPE_R6 = 110;

    static final int NTYPE_R4_ERLANG = 109;

    static final int NTYPE_R4_HIDDEN = 104;

    static final int dFlagPublished = 1;
    static final int dFlagAtomCache = 2;
    static final int dFlagExtendedReferences = 4;
    static final int dFlagDistMonitor = 8;
    static final int dFlagFunTags = 16;
    static final int dFlagDistMonitorName = 32;
    static final int dFlagHiddenAtomCache = 64;
    static final int dflagNewFunTags = 128;
    static final int dFlagExtendedPidsPorts = 256;
    static final int dFlagExportPtrTag = 512;
    static final int dFlagBitBinaries = 1024;
    static final int dFlagNewFloats = 2048;
    static final int dFlagUnicodeIo = 4096;
    static final int dFlagUtf8Atoms = 65536;
    static final int dFlagMapTag = 131072;
    /*  69 */   static String localHost = null;
    /*  74 */   static String defaultCookie = null;


    static {

        try {
            /* 111 */
            localHost = InetAddress.getLocalHost().getHostName();




            /* 116 */
            int dot = localHost.indexOf(".");
            /* 117 */
            if (dot != -1) {
                /* 118 */
                localHost = localHost.substring(0, dot);

            }
            /* 120 */
        } catch (UnknownHostException e) {
            /* 121 */
            localHost = "localhost";

        }

        /* 124 */
        String homeDir = getHomeDir();
        /* 125 */
        String dotCookieFilename = homeDir + File.separator + ".erlang.cookie";

        /* 127 */
        BufferedReader br = null;


        try {
            /* 130 */
            File dotCookieFile = new File(dotCookieFilename);

            /* 132 */
            br = new BufferedReader(new FileReader(dotCookieFile));
            /* 133 */
            String line = br.readLine();
            /* 134 */
            if (line == null) {
                /* 135 */
                defaultCookie = "";

            } else {
                /* 137 */
                defaultCookie = line.trim();

            }
            /* 139 */
        } catch (IOException e) {
            /* 140 */
            defaultCookie = "";

        } finally {

            try {
                /* 143 */
                if (br != null) {
                    /* 144 */
                    br.close();

                }
                /* 146 */
            } catch (IOException iOException) {
            }

        }

    }


    final OtpTransportFactory transportFactory;
    String node;
    String host;
    String alive;
    String cookie;
    /*  99 */ int ntype = 110;
    /* 100 */ int proto = 0;
    /* 101 */ int distHigh = 5;
    /* 102 */ int distLow = 5;
    /* 103 */ int creation = 0;
    /* 104 */ int flags = 200084;


    /* 152 */
    protected AbstractNode(OtpTransportFactory transportFactory) {
        this.transportFactory = transportFactory;
    }


    /* 160 */
    protected AbstractNode(String node) {
        this(node, defaultCookie, new OtpSocketTransportFactory());
    }


    /* 169 */
    protected AbstractNode(String node, OtpTransportFactory transportFactory) {
        this(node, defaultCookie, transportFactory);
    }


    /* 176 */
    protected AbstractNode(String name, String cookie) {
        this(name, cookie, new OtpSocketTransportFactory());
    }


    protected AbstractNode(String name, String cookie, OtpTransportFactory transportFactory) {
        /* 184 */
        this.cookie = cookie;
        /* 185 */
        this.transportFactory = transportFactory;

        /* 187 */
        int i = name.indexOf('@');
        /* 188 */
        if (i < 0) {
            /* 189 */
            this.alive = name;
            /* 190 */
            this.host = localHost;

        } else {
            /* 192 */
            this.alive = name.substring(0, i);
            /* 193 */
            this.host = name.substring(i + 1);

        }

        /* 196 */
        if (this.alive.length() > 255) {
            /* 197 */
            this.alive = this.alive.substring(0, 255);

        }

        /* 200 */
        this.node = this.alive + "@" + this.host;

    }


    private static String getHomeDir() {
        /* 285 */
        String home = System.getProperty("user.home");
        /* 286 */
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            /* 287 */
            String drive = System.getenv("HOMEDRIVE");
            /* 288 */
            String path = System.getenv("HOMEPATH");
            /* 289 */
            return (drive != null && path != null) ? (drive + path) : home;

        }
        /* 291 */
        return home;

    }


    /* 209 */
    public String node() {
        return this.node;
    }


    /* 220 */
    public String host() {
        return this.host;
    }


    /* 231 */
    public String alive() {
        return this.alive;
    }


    /* 240 */
    public String cookie() {
        return this.cookie;
    }


    /* 245 */   int type() {
        return this.ntype;
    }


    /* 250 */   int distHigh() {
        return this.distHigh;
    }


    /* 255 */   int distLow() {
        return this.distLow;
    }


    /* 260 */   int proto() {
        return this.proto;
    }


    /* 265 */   int creation() {
        return this.creation;
    }


    public String setCookie(String cookie) {
        /* 274 */
        String prev = this.cookie;
        /* 275 */
        this.cookie = cookie;
        /* 276 */
        return prev;

    }


    /* 281 */
    public String toString() {
        return node();
    }


    /* 296 */
    public OtpTransport createTransport(String addr, int port) throws IOException {
        return this.transportFactory.createTransport(addr, port);
    }


    /* 301 */
    public OtpTransport createTransport(InetAddress addr, int port) throws IOException {
        return this.transportFactory.createTransport(addr, port);
    }


    /* 306 */
    public OtpServerTransport createServerTransport(int port) throws IOException {
        return this.transportFactory.createServerTransport(port);
    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\AbstractNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */