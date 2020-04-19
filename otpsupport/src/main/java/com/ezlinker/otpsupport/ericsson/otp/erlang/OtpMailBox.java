
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class OtpMailBox {
    OtpNode home;
    OtpErlangPid self;
    GenericQueue queue;
    String name;
    Links links;


    OtpMailBox(OtpNode home, OtpErlangPid self, String name) {
        /*  91 */
        this.self = self;
        /*  92 */
        this.home = home;
        /*  93 */
        this.name = name;
        /*  94 */
        this.queue = new GenericQueue();
        /*  95 */
        this.links = new Links(10);

    }


    /* 101 */   OtpMailBox(OtpNode home, OtpErlangPid self) {
        this(home, self, null);
    }


    /* 120 */
    public OtpErlangPid self() {
        return this.self;
    }


    /* 139 */
    public synchronized boolean registerName(String aname) {
        return this.home.registerName(aname, this);
    }


    /* 149 */
    public String getName() {
        return this.name;
    }


    public OtpErlangObject receive() throws OtpErlangException {

        /* 168 */
        return receiveMsg().getMsg();
        /* 169 */

    }


    public OtpErlangObject receive(long timeout) throws OtpErlangExit, OtpErlangDecodeException {

        try {
            /* 196 */
            OtpMsg m = receiveMsg(timeout);
            /* 197 */
            if (m != null) {
                /* 198 */
                return m.getMsg();

            }
            /* 200 */
        } catch (OtpErlangExit | OtpErlangDecodeException e) {
            /* 201 */
            throw e;
            /* 202 */
        } /* 203 */ /* 204 */ catch (InterruptedException ignored) {
        }

        /* 206 */
        return null;

    }


    /* 221 */
    public OtpInputStream receiveBuf() throws OtpErlangException {
        return receiveMsg().getMsgBuf();
    }


    public OtpInputStream receiveBuf(long timeout) throws InterruptedException, OtpErlangExit {
        /* 244 */
        OtpMsg m = receiveMsg(timeout);
        /* 245 */
        if (m != null) {
            /* 246 */
            return m.getMsgBuf();

        }

        /* 249 */
        return null;

    }

    /**
     * 3-27 update
     *
     * @return
     * @throws OtpErlangExit
     */

    public OtpMsg receiveMsg() throws OtpErlangException {
        OtpMsg m = (OtpMsg) this.queue.get();

        if (m != null) {
            switch (m.type()) {
                case 3:
                case 8:
                    try {
                        OtpErlangObject o = m.getMsg();
                        throw new OtpErlangExit(o, m.getSenderPid());
                    } catch (OtpErlangDecodeException e) {
                        throw new OtpErlangExit("unknown", m.getSenderPid());
                    }
            }
        }
        return m;
    }


    public OtpMsg receiveMsg(long timeout) throws InterruptedException, OtpErlangExit {
        /* 301 */
        OtpMsg m = (OtpMsg) this.queue.get(timeout);

        /* 303 */
        if (m == null) {
            /* 304 */
            return null;

        }

        /* 307 */
        switch (m.type()) {

            case 3:

            case 8:

                try {
                    /* 311 */
                    OtpErlangObject o = m.getMsg();
                    /* 312 */
                    throw new OtpErlangExit(o, m.getSenderPid());
                    /* 313 */
                } catch (OtpErlangDecodeException e) {
                    /* 314 */
                    throw new OtpErlangExit("unknown", m.getSenderPid());

                }

        }

        /* 318 */
        return m;

    }


    public void send(OtpErlangPid to, OtpErlangObject msg) {

        try {
            /* 336 */
            String node = to.node();
            /* 337 */
            if (node.equals(this.home.node())) {
                /* 338 */
                this.home.deliver(new OtpMsg(to, (OtpErlangObject) msg.clone()));

            } else {
                /* 340 */
                OtpCookedConnection conn = this.home.getConnection(node);
                /* 341 */
                if (conn == null) {

                    return;

                }
                /* 344 */
                conn.send(this.self, to, msg);

            }
            /* 346 */
        } catch (Exception ignored) {
        }

    }


    /* 362 */
    public void send(String aname, OtpErlangObject msg) {
        this.home.deliver(new OtpMsg(this.self, aname, (OtpErlangObject) msg.clone()));
    }


    public void send(String aname, String node, OtpErlangObject msg) {

        try {
            /* 382 */
            String currentNode = this.home.node();
            /* 383 */
            if (node.equals(currentNode)) {
                /* 384 */
                send(aname, msg);
                /* 385 */
            } else if (node.indexOf('@') < 0 && node
/* 386 */.equals(currentNode.substring(0, currentNode
/* 387 */.indexOf('@')))) {
                /* 388 */
                send(aname, msg);

            } else {

                /* 391 */
                OtpCookedConnection conn = this.home.getConnection(node);
                /* 392 */
                if (conn == null) {

                    return;

                }
                /* 395 */
                conn.send(this.self, aname, msg);

            }
            /* 397 */
        } catch (Exception ignored) {
        }

    }


    /* 420 */
    public void exit() {
        this.home.closeMailBox(this);
    }


    /* 448 */
    public void exit(OtpErlangPid to, OtpErlangObject reason) {
        exit(2, to, reason);
    }


    /* 460 */
    public void exit(OtpErlangPid to, String reason) {
        exit(to, new OtpErlangAtom(reason));
    }


    private void exit(int arity, OtpErlangPid to, OtpErlangObject reason) {

        try {
            /* 468 */
            String node = to.node();
            /* 469 */
            if (node.equals(this.home.node())) {
                /* 470 */
                this.home.deliver(new OtpMsg(3, this.self, to, reason));

            } else {
                /* 472 */
                OtpCookedConnection conn = this.home.getConnection(node);
                /* 473 */
                if (conn == null) {

                    return;

                }
                /* 476 */
                switch (arity) {

                    case 1:
                        /* 478 */
                        conn.exit(this.self, to, reason);

                        break;


                    case 2:
                        /* 482 */
                        conn.exit2(this.self, to, reason);

                        break;

                }

            }
            /* 486 */
        } catch (Exception exception) {
        }

    }


    public void link(OtpErlangPid to) throws OtpErlangExit {

        try {
            /* 521 */
            String node = to.node();
            /* 522 */
            if (node.equals(this.home.node())) {
                /* 523 */
                if (!this.home.deliver(new OtpMsg(1, this.self, to))) {
                    /* 524 */
                    throw new OtpErlangExit("noproc", to);

                }

            } else {
                /* 527 */
                OtpCookedConnection conn = this.home.getConnection(node);
                /* 528 */
                if (conn != null) {
                    /* 529 */
                    conn.link(this.self, to);

                } else {
                    /* 531 */
                    throw new OtpErlangExit("noproc", to);

                }

            }
            /* 534 */
        } catch (OtpErlangExit e) {
            /* 535 */
            throw e;
            /* 536 */
        } catch (Exception exception) {
        }


        /* 539 */
        this.links.addLink(this.self, to);

    }


    public void unlink(OtpErlangPid to) {
        /* 556 */
        this.links.removeLink(this.self, to);


        try {
            /* 559 */
            String node = to.node();
            /* 560 */
            if (node.equals(this.home.node())) {
                /* 561 */
                this.home.deliver(new OtpMsg(4, this.self, to));

            } else {
                /* 563 */
                OtpCookedConnection conn = this.home.getConnection(node);
                /* 564 */
                if (conn != null) {
                    /* 565 */
                    conn.unlink(this.self, to);

                }

            }
            /* 568 */
        } catch (Exception exception) {
        }

    }


    /* 600 */
    public boolean ping(String node, long timeout) {
        return this.home.ping(node, timeout);
    }


    /* 618 */
    public String[] getNames() {
        return this.home.getNames();
    }


    /* 634 */
    public OtpErlangPid whereis(String aname) {
        return this.home.whereis(aname);
    }


    /* 657 */
    public void close() {
        this.home.closeMailBox(this);
    }


    protected void finalize() {
        /* 662 */
        close();
        /* 663 */
        this.queue.flush();

    }


    public boolean equals(Object o) {
        /* 674 */
        if (!(o instanceof OtpMailBox)) {
            /* 675 */
            return false;

        }

        /* 678 */
        OtpMailBox m = (OtpMailBox) o;
        /* 679 */
        return m.self.equals(this.self);

    }


    /* 684 */
    public int hashCode() {
        return this.self.hashCode();
    }


    void deliver(OtpMsg m) {
        /* 695 */
        switch (m.type()) {

            case 1:
                /* 697 */
                this.links.addLink(this.self, m.getSenderPid());

                return;


            case 4:
                /* 701 */
                this.links.removeLink(this.self, m.getSenderPid());

                return;


            case 3:
                /* 705 */
                this.links.removeLink(this.self, m.getSenderPid());
                /* 706 */
                this.queue.put(m);

                return;

        }


        /* 711 */
        this.queue.put(m);

    }


    void breakLinks(OtpErlangObject reason) {
        /* 718 */
        Link[] l = this.links.clearLinks();

        /* 720 */
        if (l != null) {
            /* 721 */
            int len = l.length;

            /* 723 */
            for (int i = 0; i < len; i++)
                /* 724 */
                exit(1, l[i].remote(), reason);

        }

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpMbox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */