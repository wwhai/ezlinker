package com.ezlinker.app.modules.schedule.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 *
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

    private static final long serialVersionUID=1L;

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
     * 任务描述
     */
    private String taskDescription;

    /**
     * CronTab表达式
     */
    @NotEmpty(message = "表达式不可为空")
    private String triggerCronExpression;

    /**
     * 指令内容
     */
    @NotEmpty(message = "指令不可为空")
    private String scheduleData;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
