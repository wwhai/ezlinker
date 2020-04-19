package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * aliyun 配置
 * </p>
 *
 * @author dropliu
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_aliyun_sms_config")
public class AliyunSmsConfig extends XEntity {

    private static final long serialVersionUID = 1L;


    @NotEmpty(message = "区域不可为空")
    private String regionId;

    @NotEmpty(message = "账户名不可为空")
    private String accountName;

    @TableField(value = "`from`")
    @NotEmpty(message = "发信人不可为空")
    private String from;

    @NotEmpty(message = "地址类型不可为空")
    private String addressType;

    @NotEmpty(message = "标签不可为空")
    private String tagName;

}
