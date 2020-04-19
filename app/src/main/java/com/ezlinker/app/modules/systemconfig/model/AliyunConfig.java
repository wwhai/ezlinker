package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

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
@TableName("ez_aliyun_config")
public class AliyunConfig extends XEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "阿里云访问key不可为空")
    private String accessKey;

    @NotEmpty(message = "阿里云密钥不可为空")
    private String secret;

    private String description;


}
