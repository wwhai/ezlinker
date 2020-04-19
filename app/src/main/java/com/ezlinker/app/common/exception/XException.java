package com.ezlinker.app.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: ezlinker
 * @description: 通用异常
 * @author: wangwenhai
 * @create: 2019-09-12 16:46
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class XException extends Exception {
    private String message;
    private String i18nMessage;
    private Integer code;

    public XException() {
    }

    public XException(Integer code, String message, String i18nMessage) {
        this.message = message;
        this.code = code;
        this.i18nMessage = i18nMessage;
    }

    public XException(String message, String i18nMessage) {
        this.code = 503;
        this.message = message;
        this.i18nMessage = i18nMessage;
    }


}
