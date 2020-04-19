package com.ezlinker.app.modules.moduletemplate.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import com.ezlinker.app.modules.module.pojo.DataArea;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 产品上面的模块模板
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "ez_module_template", autoResultMap = true)
public class ModuleTemplate extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 名称
     */
    private String name;

    /**
     * 数据域
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<DataArea> dataAreas;

    /**
     * 描述
     */
    private String description;

    /**
     * 如果是组的数量,前端提交辅助字段
     * 不参与Model
     */
    @TableField(exist = false)
    private Integer count = 1;

    @NotNull(message = "模块类型不可为空值")

    private String type;

    /**
     * 自定义类型 CUSTOMIZE
     * 按钮 BUTTON
     * 按钮组 BUTTON_GROUP
     * 开关 SWITCH
     * 开关组 SWITCH_GROUP
     * 进度条 PROGRESS_BAR
     * 数据体 DATA_ENTITY
     * 视频流 STREAM
     */

    public static class ModuleType {
        public static final String
                CUSTOMIZE = "CUSTOMIZE",
                BUTTON = "BUTTON",
                BUTTON_GROUP = "BUTTON_GROUP",
                SWITCH = "SWITCH",
                SWITCH_GROUP = "SWITCH_GROUP",
                PROGRESS_BAR = "PROGRESS_BAR",
                DATA_ENTITY = "DATA_ENTITY",
                STREAM = "STREAM";
    }

    /**
     * 1	0	自定义类型	https://s1.ax1x.com/2020/03/19/8yUsN8.png	CUSTOMIZE	自定义类型	0	0	2020-03-17 22:10:08
     * 2	1	按钮	https://s1.ax1x.com/2020/03/19/8yUajA.png	BUTTON	按钮	0	0	2020-03-17 22:10:08
     * 3	1	按钮组	https://s1.ax1x.com/2020/03/19/8yUUcd.png	BUTTON_GROUP	按钮组	0	0	2020-03-17 22:10:08
     * 4	1	开关	https://s1.ax1x.com/2020/03/19/8yUwnI.png	SWITCH	开关	0	0	2020-03-17 22:10:08
     * 5	1	开关组	https://s1.ax1x.com/2020/03/19/8yUvHx.png	SWITCH_GROUP	开关组	0	0	2020-03-17 22:10:08
     * 6	1	进度条	https://s1.ax1x.com/2020/03/19/8yU0Bt.png	PROGRESS_BAR	进度条	0	0	2020-03-17 22:10:08
     * 7	2	数据体	https://s1.ax1x.com/2020/03/19/8yUrAf.png	DATA_ENTITY	数据体	0	0	2020-03-17 22:10:08
     * 8	3	视频流	https://s1.ax1x.com/2020/03/19/8yUN1H.png	STREAM	视频流	0	0	2020-03-17 22:10:08
     */
    public interface Icon {
        String CUSTOMIZE = "https://s1.ax1x.com/2020/03/19/8yUsN8.png";
        String BUTTON = "https://s1.ax1x.com/2020/03/19/8yUajA.png";
        String BUTTON_GROUP = "https://s1.ax1x.com/2020/03/19/8yUUcd.png";
        String SWITCH = "https://s1.ax1x.com/2020/03/19/8yUwnI.png";
        String SWITCH_GROUP = "https://s1.ax1x.com/2020/03/19/8yUvHx.png";
        String PROGRESS_BAR = "https://s1.ax1x.com/2020/03/19/8yU0Bt.png";
        String DATA_ENTITY = "https://s1.ax1x.com/2020/03/19/8yUrAf.png";
        String STREAM = "https://s1.ax1x.com/2020/03/19/8yUN1H.png";
    }

    /**
     * 图标
     */
    private String icon;

}
