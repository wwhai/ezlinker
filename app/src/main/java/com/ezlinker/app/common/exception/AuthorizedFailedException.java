package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 登录异常
 * @create 2019-11-20 22:04
 **/
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "禁止访问")
public class AuthorizedFailedException extends XException {

    public AuthorizedFailedException(String message, String i18nMessage) {
        this.setCode(401);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }
}
