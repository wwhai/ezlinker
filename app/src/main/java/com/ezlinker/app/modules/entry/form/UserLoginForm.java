package com.ezlinker.app.modules.entry.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: ezlinker
 * @description: 用户登陆表单
 * @author: wangwenhai
 * @create: 2019-11-11 17:46
 **/
@Data
public class UserLoginForm {

    @NotEmpty(message = "请输入用户名")
    private String username;
    @NotEmpty(message = "请输入密码")
    private String password;
    @NotEmpty(message = "请输入验证码")
    private String verifyCode;
}
