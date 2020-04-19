package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信小程序密钥配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_wx_app_config")
public class WxAppConfig extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * EZLINKER授权密钥名称
     */
    private String name;

    /**
     * EZLINKER授权密钥
     */
    private String token;

    /**
     * 描述
     */
    private String description;


}
