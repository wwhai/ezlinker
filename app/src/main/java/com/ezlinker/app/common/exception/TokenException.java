package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description Token引起的异常
 * @create 2019-11-20 22:46
 **/

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Token异常,禁止访问")

public class TokenException extends XException {
    public TokenException(String message, String i18nMessage) {
        this.setCode(401);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }
}
