package com.ezlinker.app;

import com.ezlinker.app.common.utils.OSMonitor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OS {
    @Test
    public void OSInfo() {
        System.out.println(OSMonitor.getNetworkState());
    }
}
