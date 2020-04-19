package com.ezlinker.app.common.exchange;

/**
 * @program: ezlinker
 * @description: 统一返回代码描述
 * @author: wangwenhai
 * @create: 2019-11-06 14:36
 **/
public enum RCode {

    /**
     * 统一成功
     */
    SUCCESS(200, "Operation success", "操作成功"),
    FAIL(503, "Operation fail", "操作失败");

    /**
     * 代码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 国际化提示
     */
    private String i8nMessage;

    RCode(Integer code, String message, String i8nMessage) {
        this.code = code;
        this.message = message;
        this.i8nMessage = i8nMessage;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getI8nMessage() {
        return i8nMessage;
    }

    public void setI8nMessage(String i8nMessage) {
        this.i8nMessage = i8nMessage;
    }
}
