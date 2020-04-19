
package com.ezlinker.otpsupport.ericsson.otp.erlang;


class Links {
    Link[] links;
    int count;


    /*  28 */   Links() {
        this(10);
    }


    Links(int initialSize) {
        /*  32 */
        this.links = new Link[initialSize];
        /*  33 */
        this.count = 0;

    }


    synchronized void addLink(OtpErlangPid local, OtpErlangPid remote) {
        /*  38 */
        if (find(local, remote) == -1) {
            /*  39 */
            if (this.count >= this.links.length) {
                /*  40 */
                Link[] tmp = new Link[this.count * 2];
                /*  41 */
                System.arraycopy(this.links, 0, tmp, 0, this.count);
                /*  42 */
                this.links = tmp;

            }
            /*  44 */
            this.links[this.count++] = new Link(local, remote);

        }

    }


    synchronized void removeLink(OtpErlangPid local, OtpErlangPid remote) {

        int i;
        /*  52 */
        if ((i = find(local, remote)) != -1) {
            /*  53 */
            this.count--;
            /*  54 */
            this.links[i] = this.links[this.count];
            /*  55 */
            this.links[this.count] = null;

        }

    }


    /*  61 */
    synchronized boolean exists(OtpErlangPid local, OtpErlangPid remote) {
        return (find(local, remote) != -1);
    }


    synchronized int find(OtpErlangPid local, OtpErlangPid remote) {
        /*  65 */
        for (int i = 0; i < this.count; i++) {
            /*  66 */
            if (this.links[i].equals(local, remote)) {
                /*  67 */
                return i;

            }

        }
        /*  70 */
        return -1;

    }


    /*  74 */   int count() {
        return this.count;
    }


    synchronized OtpErlangPid[] localPids() {
        /*  79 */
        OtpErlangPid[] ret = null;
        /*  80 */
        if (this.count != 0) {
            /*  81 */
            ret = new OtpErlangPid[this.count];
            /*  82 */
            for (int i = 0; i < this.count; i++) {
                /*  83 */
                ret[i] = this.links[i].local();

            }

        }
        /*  86 */
        return ret;

    }


    synchronized OtpErlangPid[] remotePids() {
        /*  91 */
        OtpErlangPid[] ret = null;
        /*  92 */
        if (this.count != 0) {
            /*  93 */
            ret = new OtpErlangPid[this.count];
            /*  94 */
            for (int i = 0; i < this.count; i++) {
                /*  95 */
                ret[i] = this.links[i].remote();

            }

        }
        /*  98 */
        return ret;

    }


    synchronized Link[] clearLinks() {
        /* 103 */
        Link[] ret = null;
        /* 104 */
        if (this.count != 0) {
            /* 105 */
            ret = new Link[this.count];
            /* 106 */
            for (int i = 0; i < this.count; i++) {
                /* 107 */
                ret[i] = this.links[i];
                /* 108 */
                this.links[i] = null;

            }
            /* 110 */
            this.count = 0;

        }
        /* 112 */
        return ret;

    }


    synchronized Link[] links() {
        /* 117 */
        Link[] ret = null;
        /* 118 */
        if (this.count != 0) {
            /* 119 */
            ret = new Link[this.count];
            /* 120 */
            System.arraycopy(this.links, 0, ret, 0, this.count);

        }
        /* 122 */
        return ret;

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\Links.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */