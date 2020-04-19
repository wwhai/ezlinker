package com.ezlinker.app.modules.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_user")
@FieldNameConstants

public class User extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不可为空")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 实名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户类型：普通用户【1】，企业用户【2】，VIP用户【3】
     */
    private Integer userType;

    /**
     * 账户状态：正常【1】，冻结【2】，过期【3】
     */
    private Integer status;

    /**
     * 扩展信息
     */
    private Long userProfileId;

    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;


}
