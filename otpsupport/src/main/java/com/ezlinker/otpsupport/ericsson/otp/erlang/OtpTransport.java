package com.ezlinker.otpsupport.ericsson.otp.erlang;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface OtpTransport {
    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    void close() throws IOException;
}


/* Location:              C:\Users\admin\Desktop\jinterface-1.6.1.jar!\com\ericsson\otp\erlang\OtpTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.2
 */