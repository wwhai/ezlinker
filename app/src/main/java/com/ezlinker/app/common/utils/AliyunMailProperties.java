package com.ezlinker.app.common.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: ezlinker
 * @description: 阿里云短信配置
 * @author: wangwenhai
 * @create: 2019-11-11 09:07
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.mail")

@Data
public class AliyunMailProperties {
    @Value("${aliyun.mail.access-key}")
    private String accessKey;

    @Value("${aliyun.mail.secret}")
    private String secret;

    @Value("${aliyun.mail.region-id}")
    private String regionId;

    @Value("${aliyun.mail.account-name}")
    private String accountName;

    @Value("${aliyun.mail.from-alias}")
    private String fromAlias;

    @Value("${aliyun.mail.address-type}")
    private Integer addressType;

    @Value("${aliyun.mail.tag-name}")
    private String tagName;

}
