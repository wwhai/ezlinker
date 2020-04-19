package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @program: ezlinker
 * @description: 密码错误
 * @author: wangwenhai
 * @create: 2019-12-13 14:35
 **/
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "密码错误")

public class PasswordInvalidException extends XException {

    public PasswordInvalidException(String message, String i18nMessage) {
        this.setCode(400);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }
}
