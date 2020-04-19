package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 模块类型配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_module_type_config")
public class ModuleTypeConfig extends XEntity {
    /**
     * 自定义 0
     * 控件 1
     * 视图 2
     * 静态展示类（视频流） 3
     */
    public static final int CUSTOMIZE = 0;
    public static final int COMPONENT = 1;
    public static final int VIEW = 2;
    public static final int GRAPHICS = 3;


    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    private String label;

    /**
     * 名称
     */
    private String name;

    /**
     * 配置值
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;


    /**
     * logo
     */
    private String icon;

}
