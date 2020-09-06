package com.ezlinker.app.modules.cloudfunc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.cloudfunc.model.CloudFunction;
import com.ezlinker.app.modules.cloudfunc.service.ICloudFunctionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 云函数，云端运行脚本代码
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/cloudFunctions")
public class CloudFunctionController extends CurdController<CloudFunction> {

    @Resource
    ICloudFunctionService iCloudFunctionService;

    public CloudFunctionController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R create(@RequestBody CloudFunction cloudFunction) throws XException {
        return super.create(cloudFunction);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        CloudFunction cloudFunction = iCloudFunctionService.getById(id);
        if (cloudFunction == null) {
            throw new BizException("CloudFunction not exists", "云函数不存在");

        }
        return data(cloudFunction);
    }


    /**
     * 获取云函数列表
     *
     * @param current
     * @param size
     * @param label
     * @return
     * @throws XException
     */
    @GetMapping
    protected R queryForPage(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer size,
                             @RequestParam(required = false) Boolean enable,
                             @RequestParam(required = false) String label) {

        return data(iCloudFunctionService.page(new Page<>(current, size),
                new QueryWrapper<CloudFunction>().eq(enable != null, "enable", enable)
                        .like(label != null, "label", label)));
    }


}

