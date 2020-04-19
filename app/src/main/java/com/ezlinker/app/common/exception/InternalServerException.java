package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 服务器内部错误
 * @create 2019-11-20 22:19
 **/
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "服务器内部错误")

public class InternalServerException extends XException {

    public InternalServerException(String message, String i18nMessage) {
        this.setCode(500);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }

}
