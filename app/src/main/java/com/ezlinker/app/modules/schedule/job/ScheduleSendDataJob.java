package com.ezlinker.app.modules.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @program: ezlinker
 * @description: 定时发送指令
 * @author: wangwenhai
 * @create: 2019-12-27 17:58
 **/
public class ScheduleSendDataJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("定时任务开始启动:" + new Date());
        System.out.println("发送的数据:" + jobExecutionContext.getJobDetail().getJobDataMap().toString());
    }
}
