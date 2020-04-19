/*    */
package com.ezlinker.otpsupport.ericsson.otp.erlang;
/*    */
/*    */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
/*    */ public class OtpServerSocketTransport
        /*    */ implements OtpServerTransport
        /*    */ {
    /*    */   private final ServerSocket socket;

    /*    */
    /* 43 */
    public OtpServerSocketTransport(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 50 */
    public int getLocalPort() {
        return this.socket.getLocalPort();
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    public OtpTransport accept() throws IOException {
        /* 57 */
        Socket sock = this.socket.accept();
        /* 58 */
        sock.setTcpNoDelay(true);
        /* 59 */
        return new OtpSocketTransport(sock);
        /*    */
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 66 */
    public void close() throws IOException {
        this.socket.close();
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpServerSocketTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */