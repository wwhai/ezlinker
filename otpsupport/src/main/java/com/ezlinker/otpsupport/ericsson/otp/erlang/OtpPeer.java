/*    */
package com.ezlinker.otpsupport.ericsson.otp.erlang;
/*    */
/*    */

import java.io.IOException;
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
/*    */ public class OtpPeer
        /*    */ extends AbstractNode
        /*    */ {
    /* 31 */ int distChoose = 0;

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 37 */   OtpPeer(OtpTransportFactory transportFactory) {
        super(transportFactory);
    }

    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /*    */
    /* 47 */
    public OtpPeer(String node) {
        super(node);
    }

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
    /* 60 */
    public OtpPeer(String node, OtpTransportFactory transportFactory) {
        super(node, transportFactory);
    }

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
    @Deprecated
    /* 85 */ public OtpConnection connect(OtpSelf self) throws IOException, UnknownHostException, OtpAuthException {
        return new OtpConnection(self, this);
    }

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
    /* 98 */   int port() throws IOException {
        return OtpEpmd.lookupPort(this);
    }
    /*    */
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpPeer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */