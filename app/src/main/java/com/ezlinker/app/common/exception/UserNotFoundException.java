package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @program: ezlinker
 * @description: 用户不存在
 * @author: wangwenhai
 * @create: 2019-12-13 14:33
 **/
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "用户不存在")

public class UserNotFoundException extends XException {

    public UserNotFoundException(String message, String i18nMessage) {
        this.setCode(400);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }
}
