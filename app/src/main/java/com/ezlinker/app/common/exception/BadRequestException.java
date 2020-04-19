package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "请求参数异常")
public class BadRequestException extends XException {
    public BadRequestException(String message, String i18nMessage) {
        this.setCode(400);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }

}
