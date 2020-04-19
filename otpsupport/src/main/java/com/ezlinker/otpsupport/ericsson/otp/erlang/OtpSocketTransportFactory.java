/*    */
package com.ezlinker.otpsupport.ericsson.otp.erlang;
/*    */
/*    */

import java.io.IOException;
import java.net.InetAddress;

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
/*    */ public class OtpSocketTransportFactory
        /*    */ implements OtpTransportFactory
        /*    */ {
    /* 38 */
    public OtpTransport createTransport(String addr, int port) throws IOException {
        return new OtpSocketTransport(addr, port);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 46 */
    public OtpTransport createTransport(InetAddress addr, int port) throws IOException {
        return new OtpSocketTransport(addr, port);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 54 */
    public OtpServerTransport createServerTransport(int port) throws IOException {
        return new OtpServerSocketTransport(port);
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpSocketTransportFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */