package com.ezlinker.app.modules.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    /**
     * 用户名
     */
    private String username;


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
    private Integer userProfileId;

    /**
     * 上次登陆时间
     */
    private String lastLoginTime;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;

    private String region;

    private String province;

    private String city;

    private String area;

    private String address;

    private String domain;

    private String personalRemark;

    private String certification;

    private String qq;

    private String wechat;

    /**
     * 未读信息
     */
    @TableField
    Long msgCount;

}
