package com.ezlinker.app.modules.schedule.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.schedule.model.ScheduleInfo;
import com.ezlinker.app.modules.schedule.service.IScheduleInfoService;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>
 * 定时任务查询
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-27
 */
@RestController
@RequestMapping("/scheduleInfos")
public class ScheduleInfoController extends CurdController<ScheduleInfo> {

    @Resource
    IScheduleInfoService iScheduleInfoService;

//    /**
//     * Quartz 查询
//     */
//    @Resource
//    QuartzService quartzService;

    public ScheduleInfoController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R add(@RequestBody  ScheduleInfo scheduleInfo) throws XException {

        boolean ok = CronExpression.isValidExpression(scheduleInfo.getCronExpression());
        if (!ok) {
            throw new BadRequestException("Cron Expression incorrect", "Cron表达式格式错误");
        }
        boolean saved = iScheduleInfoService.save(scheduleInfo);
        if (!saved) {
            throw new BadRequestException("error", "保存失败");
        } else {
            return data(scheduleInfo);
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) {
        boolean ok = iScheduleInfoService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
    }

    /**
     * Ò
     * 获取定时任务列表
     *
     * @param current
     * @param size
     * @param deviceId
     * @return
     */
    @GetMapping
    protected R queryForPage(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer size,
                             @RequestParam Long deviceId) {

        QueryWrapper<ScheduleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("debvice_id", deviceId);
        return data(iScheduleInfoService.page(new Page<>(current, size), queryWrapper));
    }

}

