package com.ezlinker.app.modules.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户的详情
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_user_profile")
public class UserProfile extends XEntity {

    private static final long serialVersionUID=1L;

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


}
