package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.WxAppConfig;
import com.ezlinker.app.modules.systemconfig.service.IWxAppConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * EZLINKER和微信小程序交互的密钥配置表,主要用在内网
 * 这里主要是验证签名是否通过;
 * 验证流程:
 * EZLINKER小程序后台会生成一个随机字符串,用公钥加密,Host为Key,密文为Value缓存进Redis
 * 然后把加密内容发过来,接收到以后尝试解密,然后把解密内容返回,EZLINKER小程序服务器在redis拿密文是否相等
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/systemConfig/wxAppConfig")
public class WxAppConfigController extends CurdController<WxAppConfig> {

    @Resource
    IWxAppConfigService iWxAppConfigService;

    public WxAppConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

}

