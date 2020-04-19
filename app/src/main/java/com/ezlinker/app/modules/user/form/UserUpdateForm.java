package com.ezlinker.app.modules.user.form;

import lombok.Data;

/**
 * @program: ezlinker
 * @description: 更新用户表单
 * @author: wangwenhai
 * @create: 2019-11-15 11:38
 **/
@Data
public class UserUpdateForm {
    /**
     * 手机号码
     */
    String phone;

    /**
     * 邮箱
     */
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
