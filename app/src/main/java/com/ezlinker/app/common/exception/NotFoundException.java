package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 404
 * @create 2019-11-20 22:49
 **/
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "资源不存在")
public class NotFoundException extends XException {
    public NotFoundException() {
        this.setCode(404);
        this.setMessage("Resource not found");
        this.setI18nMessage("资源不存在");
    }
}
