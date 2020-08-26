package com.ezlinker.app.modules.schedule.service;

import com.ezlinker.app.modules.schedule.job.GetStateJob;
import com.ezlinker.app.modules.schedule.job.ScheduleSendDataJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: ezlinker
 * @description: QuartzService
 * @author: wangwenhai
 * @create: 2019-12-27 14:08
 **/
@Service

public class QuartzService {

    private static final String JOB_NAME_PREFIX = "JOB:";
    private static final String TRIGGER_NAME_PREFIX = "TRIGGER:";

    @Resource
    private Scheduler scheduler;

    private static JobKey getJobKey(String name, String group) {

        return new JobKey(JOB_NAME_PREFIX + "-DID:" + name, group);
    }

    private static TriggerKey getTriggerKey(String name, String group) {

        return new TriggerKey(TRIGGER_NAME_PREFIX + "-DID:" + name, group);
    }

    /**
     * @param deviceId
     * @param groupName
     * @param cronExpression
     * @param jobData
     * @throws SchedulerException
     */
    public void addSendCmdJob(Long deviceId, String groupName, String cronExpression, Map<String, Object> jobData) throws SchedulerException {
        String jobName = "-DID:" + deviceId;
        addJob(ScheduleSendDataJob.class, jobName, groupName, cronExpression, jobData);
    }

    /**
     * @param deviceId
     * @param groupName
     * @param cronExpression
     * @param jobData
     * @throws SchedulerException
     */
    public void addGetStateJob(Long deviceId, String groupName, String cronExpression, Map<String, Object> jobData) throws SchedulerException {
        String jobName = "-DID:" + deviceId;

        addJob(GetStateJob.class, jobName, groupName, cronExpression, jobData);
    }

    /**
     * @param clazz
     * @param jobName
     * @param groupName
     * @param cronExpression
     * @param jobData
     * @throws SchedulerException
     */
    private void addJob(Class<? extends Job> clazz, String jobName, String groupName, String cronExpression, Map<String, Object> jobData) throws SchedulerException {

        JobKey jobKey = new JobKey(JOB_NAME_PREFIX + jobName, groupName);
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withDescription("JUST_A_JOB")
                .withIdentity(jobKey)
                .build();
        if (jobData != null && jobData.size() > 0) {
            jobDetail.getJobDataMap().putAll(jobData);
        }
        TriggerKey triggerKey = new TriggerKey(TRIGGER_NAME_PREFIX + jobName, groupName);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing())
                .withDescription("JUST_A_TRIGGER")
                .startNow()
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * @param jobName
     * @param cronExpression
     * @param group
     */
    public void updateJob(String jobName, String cronExpression, String group) {
        try {
            TriggerKey triggerKey = new TriggerKey(jobName, group);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(getTriggerKey(jobName, group))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(String name, String group) throws SchedulerException {
        scheduler.deleteJob(getJobKey(name, group));

    }

    public void pauseJob(String name, String group) throws SchedulerException {
        scheduler.pauseJob(getJobKey(name, group));

    }

    public void resumeJob(String name, String group) throws SchedulerException {
        scheduler.resumeJob(getJobKey(name, group));

    }

    public void runJobNow(String name, String group) throws SchedulerException {

        scheduler.triggerJob(getJobKey(name, group));

    }

    public Trigger getJob(String name, String group) {
        try {
            return scheduler.getTrigger(getTriggerKey(name, group));
        } catch (SchedulerException ignored) {
            return null;
        }
    }
}
