/*    */
package com.ezlinker.otpsupport.ericsson.otp.erlang;
/*    */
/*    */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class OtpSocketTransport
        /*    */ implements OtpTransport
        /*    */ {
    /*    */   private final Socket socket;

    /*    */
    /*    */
    public OtpSocketTransport(String addr, int port) throws UnknownHostException, IOException {
        /* 47 */
        this.socket = new Socket(addr, port);
        /* 48 */
        this.socket.setTcpNoDelay(true);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    public OtpSocketTransport(InetAddress addr, int port) throws UnknownHostException, IOException {
        /* 56 */
        this.socket = new Socket(addr, port);
        /* 57 */
        this.socket.setTcpNoDelay(true);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 67 */
    public OtpSocketTransport(Socket s) {
        this.socket = s;
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 74 */
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 81 */
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 88 */
    public void close() throws IOException {
        this.socket.close();
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpSocketTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */