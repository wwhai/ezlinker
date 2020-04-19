package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 权限不足异常
 * @create 2019-11-20 22:36
 **/
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "权限不足,禁止访问")

public class PermissionForbiddenException extends XException {

    public PermissionForbiddenException(String message, String i18nMessage) {
        this.setCode(403);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }
}
