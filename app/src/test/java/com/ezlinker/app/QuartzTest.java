package com.ezlinker.app;

import com.ezlinker.app.modules.schedule.model.ScheduleInfo;
import com.ezlinker.app.modules.schedule.service.IScheduleInfoService;
import com.ezlinker.app.modules.schedule.service.QuartzService;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangwenhai
 * @date 2020/8/24
 * File description:
 */
@SpringBootTest
class QuartzTest {
    @Resource
    QuartzService quartzService;
    @Resource
    IScheduleInfoService iScheduleInfoService;

    @Test
    void hello() {
        System.out.println("hello world");
    }

    //@Test
    //@Transactional(rollbackFor = Exception.class)
    void addJob() throws SchedulerException {
        //给ID = 1的设备 添加定时器
        long deviceId = 1L;
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setJobName("task001");
        scheduleInfo.setCronExpression("0/1 * * * * ? ");
        scheduleInfo.setDeviceId(deviceId);
        Map<String, Object> map = new HashMap<>();
        map.put("K", "KK");
        map.put("V", "VV");
        scheduleInfo.setScheduleData(map);
        scheduleInfo.setDescription("a task");
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        scheduleInfo.setPoints(list);
        boolean ok = iScheduleInfoService.save(scheduleInfo);
        if (ok) {
            quartzService.addSendCmdJob(deviceId, "TestGroup", "0/1 * * * * ? ", new HashMap<>());
        }
    }

    @Test
    void deleteJob() throws SchedulerException {
        quartzService.deleteJob("1", "TestGroup");
    }

}
