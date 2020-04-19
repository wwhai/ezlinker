package com.ezlinker.app.modules.module.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流媒体推送密钥
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_stream_integration")
public class StreamIntegration extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备ID
     */
    private Long moduleId;

    /**
     * 令牌
     */
    private String token;

    /**
     * 密钥
     */
    private String secret;


}
