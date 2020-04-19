package com.ezlinker.app;

import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.emqintegeration.monitor.EMQMonitorV4;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EMQXV4APITest {

    /**
     * 测试节点是否存在
     *
     */
    @Test
    public void testNode() throws BizException {
        EmqxConfig emqxConfig = new EmqxConfig();
        emqxConfig.setIp("localhost")
                .setPort(8080)
                .setSecret("1")
                .setAppId("1")
                .setDescription("test")
                .setNodeName("node@127.0.0.1");

            EMQMonitorV4.getBrokersInfo(emqxConfig);

    }
}
