package com.ezlinker.app.config.otp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Java 的OTP节点配置
 */
@Configuration
public class JOtpNodeConfig {

    private Logger logger = LoggerFactory.getLogger(JOtpNodeConfig.class);

    /**
     * 消息发送格式
     * {ezlinker_core_node1_receive_loop,'ezlinker_core_node1@127.0.0.1'}!{self(),"Msg"}
     * {ezlinker_core_node1,"emqx1@localhost"}!{self(),"Msg"}
     *
     * @return
     * @throws IOException
     */

}
