package com.ezlinker.otpsupport.ericsson.otp.erlang;

import java.io.IOException;
import java.net.InetAddress;

public interface OtpTransportFactory {
    OtpTransport createTransport(String paramString, int paramInt) throws IOException;

    OtpTransport createTransport(InetAddress paramInetAddress, int paramInt) throws IOException;

    OtpServerTransport createServerTransport(int paramInt) throws IOException;
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpTransportFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */