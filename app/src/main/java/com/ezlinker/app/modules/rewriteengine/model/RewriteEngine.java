package com.ezlinker.app.modules.rewriteengine.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 脚本
 * </p>
 *
 * @author wangwenhai
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_rewrite_engine")
public class RewriteEngine extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * User ID
     */
    private Integer userId;
    /**
     * UI显示的标签
     */
    private String name;
    private String description;
}
