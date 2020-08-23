package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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

@TableName(value = "ez_module_template_config", autoResultMap = true)
public class ModuleTemplateConfig extends XEntity {
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

    /**
     * 事件配置
     * name: 名称
     * command: 指令
     * on_data: 当数据到达以后，这里是个回调，一般是个JS函数
     * [
     * {
     * "name": "点击",
     * "command": "on",
     * "on_data": "function(data) ->{//---}"
     * }
     * ]
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<ActionConfig> actionConfig;

    public static class ActionConfig {
        // 名称
        public String trigger;
        // 数据键名
        public String action;
        // 简单描述
        public String description;
    }
}
