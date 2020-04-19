
package com.ezlinker.otpsupport.ericsson.otp.erlang;


public class GenericQueue {
    private static final int open = 0;
    private static final int closing = 1;
    private static final int closed = 2;
    private int status;
    private Bucket head;
    private Bucket tail;
    private int count;


    public GenericQueue() {
        /*  45 */
        init();
        /*  46 */
        this.status = 0;

    }


    private void init() {
        /*  38 */
        this.head = null;
        /*  39 */
        this.tail = null;
        /*  40 */
        this.count = 0;

    }


    /*  51 */
    public void flush() {
        init();
    }


    /*  55 */
    public void close() {
        this.status = 1;
    }


    public synchronized void put(Object o) {
        /*  65 */
        Bucket b = new Bucket(o);

        /*  67 */
        if (this.tail != null) {
            /*  68 */
            this.tail.setNext(b);
            /*  69 */
            this.tail = b;

        } else {

            /*  72 */
            this.head = this.tail = b;

        }
        /*  74 */
        this.count++;


        /*  77 */
        notify();

    }


    /**
     * 3-27 :update wait() method
     *
     * @return
     */
//    public synchronized Object get() {

//        /*  87 */
//        Object o;
//        while (true) {
//            o = tryGet();
//            if (o != null) {
//                return o;
//            }
//        }
//        /* 142 */
//        Object o = null;
//
//        /* 144 */
//        if (this.head != null) {
//            /* 145 */
//            o = this.head.getContents();
//            /* 146 */
//            this.head = this.head.getNext();
//            /* 147 */
//            this.count--;
//
//            /* 149 */
//            if (this.head == null) {
//                /* 150 */
//                this.tail = null;
//                /* 151 */
//                this.count = 0;
//
//            }
//
//        }
//
//        /* 155 */
//        return o;
//
//    }
    public synchronized Object get() {

        /*  87 */
        Object o;
        while ((o = tryGet()) == null) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        return o;

    }


    public synchronized Object get(long timeout) throws InterruptedException {
        /* 114 */
        if (this.status == 2) {
            /* 115 */
            return null;

        }

        /* 118 */
        long currentTime = System.currentTimeMillis();
        /* 119 */
        long stopTime = currentTime + timeout;
        /* 120 */
        Object o = null;


        while (true) {
            /* 123 */
            if ((o = tryGet()) != null) {
                /* 124 */
                return o;

            }

            /* 127 */
            currentTime = System.currentTimeMillis();
            /* 128 */
            if (stopTime <= currentTime) {
                /* 129 */
                throw new InterruptedException("Get operation timed out");

            }


            try {
                /* 133 */
                wait(stopTime - currentTime);
                /* 134 */
            } catch (InterruptedException interruptedException) {
            }

        }

    }


    public Object tryGet() {
        /* 142 */
        Object o = null;

        /* 144 */
        if (this.head != null) {
            /* 145 */
            o = this.head.getContents();
            /* 146 */
            this.head = this.head.getNext();
            /* 147 */
            this.count--;

            /* 149 */
            if (this.head == null) {
                /* 150 */
                this.tail = null;
                /* 151 */
                this.count = 0;

            }

        }

        /* 155 */
        return o;

    }


    /* 159 */
    public synchronized int getCount() {
        return this.count;
    }


    class Bucket {

        private final Object contents;
        private Bucket next;


        public Bucket(Object o) {
            /* 171 */
            this.next = null;
            /* 172 */
            this.contents = o;

        }


        /* 180 */
        public Bucket getNext() {
            return this.next;
        }


        /* 176 */
        public void setNext(Bucket newNext) {
            this.next = newNext;
        }


        /* 184 */
        public Object getContents() {
            return this.contents;
        }

    }

}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\GenericQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */