package com.ezlinker.app.config.quartz;

import lombok.Data;

/**
 * @program: ezlinker
 * @description: 传给计划任务的数据
 * @author: wangwenhai
 * @create: 2019-12-27 17:35
 **/
@Data
public class ScheduleData {

    private String k;
    private Object v;
}
