package com.ezlinker.app.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ezlinker.app.common.exception.XException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 阿里云发邮件工具
 */

@Component
public class AliyunEmailUtil {
    @Resource
    AliyunMailProperties aliyunMailProperties;

    public void sendHtmlMail(String email, String subject, String emailContent) throws XException {
        IClientProfile profile = DefaultProfile.getProfile(aliyunMailProperties.getRegionId(),
                aliyunMailProperties.getAccessKey(), aliyunMailProperties.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        request.setAccountName(aliyunMailProperties.getAccountName());
        request.setFromAlias(aliyunMailProperties.getFromAlias());
        request.setAddressType(aliyunMailProperties.getAddressType());
        request.setTagName(aliyunMailProperties.getTagName());
        request.setReplyToAddress(true);
        request.setEncoding("utf-8");
        request.setToAddress(email);
        request.setSubject(subject);
        request.setHtmlBody(emailContent);
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new XException(501, "Email send failure", "邮件发送失败");
        }
    }
}