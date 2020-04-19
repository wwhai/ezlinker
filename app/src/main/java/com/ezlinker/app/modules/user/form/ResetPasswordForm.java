package com.ezlinker.app.modules.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: ezlinker
 * @description: 重置密码
 * @author: wangwenhai
 * @create: 2019-11-14 11:00
 **/
@Data
public class ResetPasswordForm {
    @NotEmpty(message = "旧密码不可为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不可为空")
    private String newPassword;
    @NotEmpty(message = "请确认密码")
    private String passwordRetry;
}
