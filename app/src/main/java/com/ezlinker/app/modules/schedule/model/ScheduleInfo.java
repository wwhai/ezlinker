package com.ezlinker.app.modules.schedule.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>
 * 定时任务是作用在产品之上的,业务数据
 * 生产出来设备以后由用户决定是否开启.
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "ez_schedule_info", autoResultMap = true)
public class ScheduleInfo extends XEntity {


    private static final long serialVersionUID = 1L;


    public static String DEFAULT_JOB_GROUP = "DEFAULT_JOB_GROUP";
    public static String DEFAULT_TRIGGER_GROUP = "DEFAULT_TRIGGER_GROUP";

    /**
     * 关联的产品ID
     */
    @NotNull(message = "请绑定设备")
    private Long deviceId;

    /**
     * 任务名称
     */
    @NotEmpty(message = "请驶入定时任务名称")

    private String jobName;

    /**
     * 任务组名称
     */
    @JsonIgnore
    private String jobGroup = DEFAULT_JOB_GROUP;

    /**
     * 触发器名称
     */
    @JsonIgnore
    private String triggerName;

    /**
     * 触发器组
     */
    @JsonIgnore
    private String triggerGroup = DEFAULT_TRIGGER_GROUP;

    /**
     * 表达式
     */
    @NotEmpty(message = "Cron表达式不可为空")
    private String cronExpression;

    /**
     * 目标执行类类名
     */
    @JsonIgnore
    private String executeClass;

    /**
     *
     */
    @EnumValue
    private ScheduleType status = ScheduleType.STOP;

    /**
     * 计划任务的指令
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> scheduleData;
    /**
     * 作用点
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> points;

    /**
     * 任务描述
     */
    private String description;

}
