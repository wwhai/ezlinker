package com.ezlinker.app.modules.rewriteengine.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.rewriteengine.model.RewriteEngine;
import com.ezlinker.app.modules.rewriteengine.service.IRewriteEngineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 脚本 前端控制器
 * </p>
 *
 * @author wangwenhai
 * @since 2020-09-01
 */
@RestController
@RequestMapping("/rwengines")
public class RewriteEngineController extends CurdController {
    @Resource
    IRewriteEngineService iRewriteEngineService;

    public RewriteEngineController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    @GetMapping
    protected R get(@RequestParam Long id) throws XException {
        return super.get(id);
    }


    /**
     * 条件检索
     *
     * @param current
     * @param size
     * @param name
     * @param type
     * @param description
     * @return
     */
    @GetMapping("/queryForPage")
    protected R queryForPage(@RequestParam(required = false, defaultValue = "0") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer size,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String description) {
        QueryWrapper<RewriteEngine> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(name != null, "name", name);
        queryWrapper.like(type != null, "type", type);
        queryWrapper.like(description != null, "description", description);
        IPage<RewriteEngine> page = iRewriteEngineService.page(new Page<>(current, size), queryWrapper);
        return data(page);
    }
}

