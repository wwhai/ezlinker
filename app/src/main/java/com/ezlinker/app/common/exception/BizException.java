package com.ezlinker.app.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 业务异常
 * @create 2019-11-20 23:06
 **/
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "操作失败")

public class BizException extends XException {
    public BizException(String message, String i18nMessage) {
        this.setCode(503);
        this.setMessage(message);
        this.setI18nMessage(i18nMessage);
    }


}
