package com.ezlinker.app.modules.systemconfig.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.AliyunConfig;
import com.ezlinker.app.modules.systemconfig.model.AliyunSmsConfig;
import com.ezlinker.app.modules.systemconfig.service.IAliyunConfigService;
import com.ezlinker.app.modules.systemconfig.service.IAliyunSmsConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * aliyunSmsConfig
 *
 * @author dropliu
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/systemConfig/aliyunSmsConfig")
public class AliyunSmsConfigController extends CurdController<AliyunSmsConfig> {


    @Resource
    IAliyunSmsConfigService iAliyunSmsConfigService;

    public AliyunSmsConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    @PostMapping
    protected R add(@RequestBody @Valid AliyunSmsConfig aliyunSmsConfig) throws XException {
        boolean ok = iAliyunSmsConfigService.save(aliyunSmsConfig);
        return ok?success():fail();
    }

    @Override
    @DeleteMapping
    protected R delete(@RequestBody Integer[] ids) throws XException {
        boolean ok = iAliyunSmsConfigService.removeByIds(Arrays.asList(ids));
        return ok?success():fail();
    }

    @Override
    @PutMapping("/{id}")
    protected R update(@PathVariable Long id, @RequestBody @Valid AliyunSmsConfig aliyunSmsConfig) throws XException {
        AliyunSmsConfig oldConfig = iAliyunSmsConfigService.getById(id);
        if (oldConfig==null){
            throw new BizException("配置不存在", "The config not exists");
        }

        boolean ok = iAliyunSmsConfigService.updateById(aliyunSmsConfig);
        return ok?success():fail();
    }

    @Override
    @GetMapping("/{id}")
    protected R get(@PathVariable Long id) throws XException {
        AliyunSmsConfig config = iAliyunSmsConfigService.getById(id);
        if (config==null){
            throw new BizException("配置不存在", "The config not exists");
        }
        return data(config);
    }

    @Override
    @GetMapping("/list")
    protected R list(@RequestParam Integer current,@RequestParam Integer size) {
        return data(iAliyunSmsConfigService.page(new Page<>(current, size)));
    }
}
