package com.ezlinker.app.modules.schedule.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.schedule.model.ScheduleInfo;
import com.ezlinker.app.modules.schedule.service.IScheduleInfoService;
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


    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws XException
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) throws XException {
        boolean ok = iScheduleInfoService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
    }

    /**
     * 获取定时任务列表
     *
     * @param current
     * @param size
     * @param linkId
     * @return
     */
    @GetMapping
    protected R queryForPage(@RequestParam Integer current, @RequestParam Integer size, @RequestParam Long linkId) {

        return data(iScheduleInfoService.page(new Page<>(current, size), new QueryWrapper<ScheduleInfo>().eq("link_id", linkId)));
    }

}

