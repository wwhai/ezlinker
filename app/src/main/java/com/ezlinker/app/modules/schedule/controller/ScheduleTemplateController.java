package com.ezlinker.app.modules.schedule.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.modules.schedule.model.ScheduleTemplate;
import com.ezlinker.app.modules.schedule.service.IScheduleInfoService;
import com.ezlinker.app.modules.schedule.service.IScheduleTemplateService;
import org.springframework.web.bind.annotation.*;

import com.ezlinker.app.common.web.CurdController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/scheduleTemplate")
public class ScheduleTemplateController extends CurdController<ScheduleTemplate> {

    public ScheduleTemplateController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Resource
    IScheduleTemplateService iScheduleTemplateService;

    @Override
    @PostMapping
    protected R add(@RequestBody @Valid ScheduleTemplate scheduleTemplate) throws XException {
        scheduleTemplate.setUpdatedTime(LocalDateTime.now());
        boolean ok = iScheduleTemplateService.save(scheduleTemplate);
        return ok?success():fail();
    }

    @Override
    @DeleteMapping
    protected R delete(@RequestBody Integer[] ids) throws XException {
        boolean ok = iScheduleTemplateService.removeByIds(Arrays.asList(ids));
        return ok?success():fail();
    }

    @Override
    @PutMapping("/{id}")
    protected R update(@PathVariable Long id, @RequestBody @Valid ScheduleTemplate scheduleTemplate) throws XException {
        ScheduleTemplate oldConfig = iScheduleTemplateService.getById(id);
        if (scheduleTemplate==null){
            throw new BizException("更新的scheduleTemplate不存在","The scheduleTemplate not exists");
        }
        scheduleTemplate.setUpdatedTime(LocalDateTime.now());
        boolean ok = iScheduleTemplateService.updateById(scheduleTemplate);
        return ok?data(scheduleTemplate):fail();
    }

    @Override
    @GetMapping("/{id}")
    protected R get(@PathVariable Long id) throws XException {
        ScheduleTemplate scheduleTemplate = iScheduleTemplateService.getById(id);
        if (scheduleTemplate==null){
            throw new BizException("获取的scheduleTemplate不存在", "The scheduleTemplate not exists");
        }
        return data(scheduleTemplate);
    }


    @Override
    @GetMapping("/list")
    protected R list(@RequestParam Integer current, @RequestParam Integer size) {
        return data(iScheduleTemplateService.page(new Page<>(current, size)));
    }
}

