
package com.ezlinker.otpsupport.ericsson.otp.erlang;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;


public class OtpEpmd {
    private static final byte stopReq = 115;
    private static final byte port4req = 122;
    private static final byte port4resp = 119;
    private static final byte publish4req = 120;
    private static final byte publish4resp = 121;
    private static final byte names4req = 110;

    private static final int traceThreshold = 4;


    /*  81 */   private static int traceLevel = 0;


    static {
        /*  86 */
        String trace = System.getProperties().getProperty("OtpConnection.trace");


        try {
            /*  89 */
            if (trace != null) {
                /*  90 */
                traceLevel = Integer.valueOf(trace).intValue();

            }
            /*  92 */
        } catch (NumberFormatException e) {
            /*  93 */
            traceLevel = 0;

        }

    }


    /* 108 */
    public static void useEpmdPort(int port) {
        EpmdPort.set(port);
    }


    /* 121 */
    public static int lookupPort(AbstractNode node) throws IOException {
        return r4_lookupPort(node);
    }


    public static boolean publishPort(OtpLocalNode node) throws IOException {
        /* 139 */
        OtpTransport s = null;

        /* 141 */
        s = r4_publish(node);

        /* 143 */
        node.setEpmd(s);

        /* 145 */
        return (s != null);

    }


    public static void unPublishPort(OtpLocalNode node) {
        /* 159 */
        OtpTransport s = null;


        /* 162 */
        try {
            s = node.createTransport((String) null, EpmdPort.get());

            /* 164 */
            OtpOutputStream obuf = new OtpOutputStream();
            /* 165 */
            obuf.write2BE((node.alive().length() + 1));
            /* 166 */
            obuf.write1(115L);
            /* 167 */
            obuf.writeN(node.alive().getBytes());
            /* 168 */
            obuf.writeToAndFlush(s.getOutputStream());

            /* 170 */
            if (traceLevel >= 4) {
                /* 171 */
                System.out.println("-> UNPUBLISH " + node + " port=" + node
/* 172 */.port());
                /* 173 */
                System.out.println("<- OK (assumed)");

            }
        }
        /* 175 */ catch (Exception exception) {
        } finally {
            try {
                /* 178 */
                if (s != null) {
                    /* 179 */
                    s.close();

                }
                /* 181 */
            } catch (IOException iOException) {
            }

            /* 183 */
            s = null;
        }


    }


    private static int r4_lookupPort(AbstractNode node) throws IOException {
        /* 189 */
        int port = 0;
        /* 190 */
        OtpTransport s = null;


        try {
            /* 194 */
            OtpOutputStream obuf = new OtpOutputStream();
            /* 195 */
            s = node.createTransport(node.host(), EpmdPort.get());



            /* 199 */
            obuf.write2BE((node.alive().length() + 1));
            /* 200 */
            obuf.write1(122L);
            /* 201 */
            obuf.writeN(node.alive().getBytes());


            /* 204 */
            obuf.writeToAndFlush(s.getOutputStream());

            /* 206 */
            if (traceLevel >= 4) {
                /* 207 */
                System.out.println("-> LOOKUP (r4) " + node);

            }





            /* 214 */
            byte[] tmpbuf = new byte[100];

            /* 216 */
            int n = s.getInputStream().read(tmpbuf);

            /* 218 */
            if (n < 0) {
                /* 219 */
                s.close();
                /* 220 */
                throw new IOException("Nameserver not responding on " + node
/* 221 */.host() + " when looking up " + node.alive());

            }


            /* 225 */
            OtpInputStream ibuf = new OtpInputStream(tmpbuf, 0);

            /* 227 */
            int response = ibuf.read1();
            /* 228 */
            if (response == 119) {
                /* 229 */
                int result = ibuf.read1();
                /* 230 */
                if (result == 0) {
                    /* 231 */
                    port = ibuf.read2BE();

                    /* 233 */
                    node.ntype = ibuf.read1();
                    /* 234 */
                    node.proto = ibuf.read1();
                    /* 235 */
                    node.distHigh = ibuf.read2BE();
                    /* 236 */
                    node.distLow = ibuf.read2BE();

                }


            }
            /* 240 */
        } catch (IOException e) {
            /* 241 */
            if (traceLevel >= 4) {
                /* 242 */
                System.out.println("<- (no response)");

            }
            /* 244 */
            throw new IOException("Nameserver not responding on " + node.host() + " when looking up " + node
/* 245 */.alive(), e);
            /* 246 */
        } catch (OtpErlangDecodeException e) {
            /* 247 */
            if (traceLevel >= 4) {
                /* 248 */
                System.out.println("<- (invalid response)");

            }
            /* 250 */
            throw new IOException("Nameserver not responding on " + node.host() + " when looking up " + node
/* 251 */.alive());

        } finally {

            try {
                /* 254 */
                if (s != null) {
                    /* 255 */
                    s.close();

                }
                /* 257 */
            } catch (IOException iOException) {
            }

            /* 259 */
            s = null;

        }

        /* 262 */
        if (traceLevel >= 4) {
            /* 263 */
            if (port == 0) {
                /* 264 */
                System.out.println("<- NOT FOUND");

            } else {
                /* 266 */
                System.out.println("<- PORT " + port);

            }

        }
        /* 269 */
        return port;

    }


    private static OtpTransport r4_publish(OtpLocalNode node) throws IOException {
        /* 281 */
        OtpTransport s = null;


        try {
            /* 285 */
            OtpOutputStream obuf = new OtpOutputStream();
            /* 286 */
            s = node.createTransport((String) null, EpmdPort.get());

            /* 288 */
            obuf.write2BE((node.alive().length() + 13));

            /* 290 */
            obuf.write1(120L);
            /* 291 */
            obuf.write2BE(node.port());

            /* 293 */
            obuf.write1(node.type());

            /* 295 */
            obuf.write1(node.proto());
            /* 296 */
            obuf.write2BE(node.distHigh());
            /* 297 */
            obuf.write2BE(node.distLow());

            /* 299 */
            obuf.write2BE(node.alive().length());
            /* 300 */
            obuf.writeN(node.alive().getBytes());
            /* 301 */
            obuf.write2BE(0L);


            /* 304 */
            obuf.writeToAndFlush(s.getOutputStream());

            /* 306 */
            if (traceLevel >= 4) {
                /* 307 */
                System.out.println("-> PUBLISH (r4) " + node + " port=" + node
/* 308 */.port());

            }


            /* 312 */
            byte[] tmpbuf = new byte[100];
            /* 313 */
            int n = s.getInputStream().read(tmpbuf);

            /* 315 */
            if (n < 0) {
                /* 316 */
                s.close();
                /* 317 */
                throw new IOException("Nameserver not responding on " + node
/* 318 */.host() + " when publishing " + node.alive());

            }


            /* 322 */
            OtpInputStream ibuf = new OtpInputStream(tmpbuf, 0);

            /* 324 */
            int response = ibuf.read1();
            /* 325 */
            if (response == 121) {
                /* 326 */
                int result = ibuf.read1();
                /* 327 */
                if (result == 0) {
                    /* 328 */
                    node.creation = ibuf.read2BE();
                    /* 329 */
                    if (traceLevel >= 4) {
                        /* 330 */
                        System.out.println("<- OK");

                    }
                    /* 332 */
                    return s;

                }

            }
            /* 335 */
        } catch (IOException e) {

            /* 337 */
            if (s != null) {
                /* 338 */
                s.close();

            }
            /* 340 */
            if (traceLevel >= 4) {
                /* 341 */
                System.out.println("<- (no response)");

            }
            /* 343 */
            throw new IOException("Nameserver not responding on " + node.host() + " when publishing " + node
/* 344 */.alive());
            /* 345 */
        } catch (OtpErlangDecodeException e) {
            /* 346 */
            s.close();
            /* 347 */
            if (traceLevel >= 4) {
                /* 348 */
                System.out.println("<- (invalid response)");

            }
            /* 350 */
            throw new IOException("Nameserver not responding on " + node.host() + " when publishing " + node
/* 351 */.alive());

        }

        /* 354 */
        s.close();
        /* 355 */
        return null;

    }


    /* 359 */
    public static String[] lookupNames() throws IOException {
        return lookupNames(InetAddress.getByName(null), new OtpSocketTransportFactory());
    }


    /* 365 */
    public static String[] lookupNames(OtpTransportFactory transportFactory) throws IOException {
        return lookupNames(InetAddress.getByName(null), transportFactory);
    }


    /* 370 */
    public static String[] lookupNames(InetAddress address) throws IOException {
        return lookupNames(address, new OtpSocketTransportFactory());
    }


    public static String[] lookupNames(InetAddress address, OtpTransportFactory transportFactory) throws IOException {
        /* 375 */
        OtpTransport s = null;


        try {
            /* 379 */
            OtpOutputStream obuf = new OtpOutputStream();

            try {
                /* 381 */
                s = transportFactory.createTransport(address, EpmdPort.get());

                /* 383 */
                obuf.write2BE(1L);
                /* 384 */
                obuf.write1(110L);

                /* 386 */
                obuf.writeToAndFlush(s.getOutputStream());

                /* 388 */
                if (traceLevel >= 4) {
                    /* 389 */
                    System.out.println("-> NAMES (r4) ");

                }


                /* 393 */
                byte[] buffer = new byte[256];
                /* 394 */
                ByteArrayOutputStream out = new ByteArrayOutputStream(256);

                while (true) {
                    /* 396 */
                    int bytesRead = s.getInputStream().read(buffer);
                    /* 397 */
                    if (bytesRead == -1) {

                        break;

                    }
                    /* 400 */
                    out.write(buffer, 0, bytesRead);

                }
                /* 402 */
                byte[] tmpbuf = out.toByteArray();

                /* 404 */
                OtpInputStream ibuf = new OtpInputStream(tmpbuf, 0);
                /* 405 */
                ibuf.read4BE();



                /* 409 */
                int n = tmpbuf.length;
                /* 410 */
                byte[] buf = new byte[n - 4];
                /* 411 */
                System.arraycopy(tmpbuf, 4, buf, 0, n - 4);
                /* 412 */
                String all = OtpErlangString.newString(buf);
                /* 413 */
                return all.split("\n");

            } finally {
                /* 415 */
                if (s != null) {
                    /* 416 */
                    s.close();

                }

            }

            /* 420 */
        } catch (IOException e) {
            /* 421 */
            if (traceLevel >= 4) {
                /* 422 */
                System.out.println("<- (no response)");

            }
            /* 424 */
            throw new IOException("Nameserver not responding when requesting names");

        }
        /* 426 */ catch (OtpErlangDecodeException e) {
            /* 427 */
            if (traceLevel >= 4) {
                /* 428 */
                System.out.println("<- (invalid response)");

            }
            /* 430 */
            throw new IOException("Nameserver not responding when requesting names");

        }

    }


    private static class EpmdPort {
        /*  52 */     private static int epmdPort = 0;


        public static int get() {
            /*  55 */
            if (epmdPort == 0) {

                String env;

                try {
                    /*  58 */
                    env = System.getenv("ERL_EPMD_PORT");
                    /*  59 */
                } catch (SecurityException e) {
                    /*  60 */
                    env = null;

                }
                /*  62 */
                epmdPort = (env != null) ? Integer.parseInt(env) : 4369;

            }
            /*  64 */
            return epmdPort;

        }


        /*  68 */
        public static void set(int port) {
            epmdPort = port;
        }

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpEpmd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */