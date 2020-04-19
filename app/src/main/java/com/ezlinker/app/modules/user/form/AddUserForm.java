package com.ezlinker.app.modules.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddUserForm {
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可留空")
    String username;

    /**
     * 手机号码
     */

    @NotEmpty(message = "电话号码不可留空")
    String phone;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不可留空")

    String email;

    /**
     * 实名
     */
    String realName;

    /**
     * 昵称
     */
    String nickName;
}
