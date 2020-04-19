package com.ezlinker.app.modules.schedule.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import com.ezlinker.app.config.quartz.ScheduleData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * 定时任务是作用在产品之上的,
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

    /**
     * 启动状态
     */
    public static int RUNNING = 0;
    /**
     * 暂停状态
     */
    public static int PAUSE = 1;
    /**
     * 停滞状态
     */
    public static int STOP = 2;


    /**
     * 关联的产品ID
     */
    @NotNull(message = "请绑定产品ID")
    private Long linkId;
    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 任务名称
     */
    @NotEmpty(message = "请驶入定时任务名称")

    private String taskName;

    /**
     * 任务组名称
     */
    @JsonIgnore
    private String taskGroup = "DEFAULT_GROUP";

    /**
     * 触发器名称
     */
    @JsonIgnore

    private String triggerName = "DEFAULT_TRIGGER";

    /**
     * 触发器组
     */
    @JsonIgnore

    private String triggerGroup = "DEFAULT_TRIGGER_GROUP";

    /**
     * 表达式
     */
    @NotEmpty(message = "Cron表达式不可为空")

    private String triggerCronExpression;

    /**
     * 目标执行类类名
     */
    @JsonIgnore

    private String executeClassName;

    /**
     * 执行类的具体执行方法
     */
    @JsonIgnore

    private String executeMethodName;

    /**
     * 数据目标所在表集合","分割用于统计
     */
    @JsonIgnore

    private String targetTable;

    /**
     * 是否启动
     */

    private Boolean isStart = false;

    /**
     * 0删，1允正常
     */
    private Integer status = STOP;

    /**
     * 创建人id
     */

    private String updatedId;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 计划任务的指令
     */
    @NotEmpty(message = "数据域不可为空")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<ScheduleData> scheduleData;


}
