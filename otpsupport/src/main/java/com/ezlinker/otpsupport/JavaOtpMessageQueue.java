package com.ezlinker.otpsupport;

public class JavaOtpMessageQueue {
    private static final int open = 0;
    private static final int closing = 1;
    private static final int closed = 2;
    private int status;
    private JavaOtpMessageQueue.Bucket head;
    private JavaOtpMessageQueue.Bucket tail;
    private int count;

    public JavaOtpMessageQueue() {
        this.init();
        this.status = 0;
    }

    private void init() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public void flush() {
        this.init();
    }

    public void close() {
        this.status = 1;
    }

    public synchronized void put(Object o) {
        JavaOtpMessageQueue.Bucket b = new JavaOtpMessageQueue.Bucket(o);
        if (this.tail != null) {
            this.tail.setNext(b);
            this.tail = b;
        } else {
            this.head = this.tail = b;
        }

        ++this.count;
        this.notify();
    }

    public synchronized Object get() {
        Object o = null;

        while ((o = this.tryGet()) == null) {
            try {
                this.wait();
            } catch (InterruptedException var3) {
            }
        }

        return o;
    }

    public synchronized Object get(long timeout) throws InterruptedException {
        if (this.status == 2) {
            return null;
        } else {
            long currentTime = System.currentTimeMillis();
            long stopTime = currentTime + timeout;
            Object o = null;

            while ((o = this.tryGet()) == null) {
                currentTime = System.currentTimeMillis();
                if (stopTime <= currentTime) {
                    throw new InterruptedException("Get operation timed out");
                }

                try {
                    this.wait(stopTime - currentTime);
                } catch (InterruptedException var9) {
                }
            }

            return o;
        }
    }

    public Object tryGet() {
        Object o = null;
        if (this.head != null) {
            o = this.head.getContents();
            this.head = this.head.getNext();
            --this.count;
            if (this.head == null) {
                this.tail = null;
                this.count = 0;
            }
        }

        return o;
    }

    public synchronized int getCount() {
        return this.count;
    }

    class Bucket {
        private final Object contents;
        private JavaOtpMessageQueue.Bucket next = null;

        public Bucket(Object o) {
            this.contents = o;
        }

        public JavaOtpMessageQueue.Bucket getNext() {
            return this.next;
        }

        public void setNext(JavaOtpMessageQueue.Bucket newNext) {
            this.next = newNext;
        }

        public Object getContents() {
            return this.contents;
        }
    }
}
