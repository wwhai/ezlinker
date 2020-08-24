package com.ezlinker.app.modules.schedule.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

/**
 * <p>
 * 模板，不具备业务功能，仅仅为了保存一些将来Copy的数据字段值
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_schedule_template")
public class ScheduleTemplate extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 定时任务作用的产品
     */
    @NotNull(message = "产品不可为空")
    private Integer productId;

    /**
     * 任务名称
     */
    @NotEmpty(message = "任务名称不可为空")
    private String taskName;

    /**
     * CronTab表达式
     */
    @NotEmpty(message = "表达式不可为空")
    private String cronExpression;

    /**
     * 计划任务的指令
     */
    @NotEmpty(message = "指令不可为空")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private HashMap<String, Object> scheduleData;

    /**
     * 任务描述
     */
    private String taskDescription;

}
