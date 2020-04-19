package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 可视化视觉展示效果
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_visual_style_config")
public class VisualStyleConfig extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 可视化视觉名称
     */
    private String name;

    /**
     * UI文本
     */
    private String label;

    /**
     * 图标
     */
    private String icon;

    /**
     * 可视化视觉的描述
     */
    private String description;


}
