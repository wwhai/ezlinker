package com.ezlinker.app.modules.systemconfig.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.AliyunConfig;
import com.ezlinker.app.modules.systemconfig.service.IAliyunConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/systemConfig/aliyunConfig")
public class AliyunConfigController extends CurdController<AliyunConfig> {


    @Resource
    IAliyunConfigService iAliyunConfigService;

    public AliyunConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    @PostMapping
    protected R add(@RequestBody @Valid AliyunConfig aliyunConfig) throws XException {
        boolean ok = iAliyunConfigService.save(aliyunConfig);
        return ok?success():fail();
    }

    @Override
    @DeleteMapping
    protected R delete(@RequestBody Integer[] ids) throws XException {
        boolean ok = iAliyunConfigService.removeByIds(Arrays.asList(ids));
        return ok?success():fail();
    }

    @Override
    @PutMapping("/{id}")
    protected R update(@PathVariable Long id, @RequestBody AliyunConfig aliyunConfig) throws XException {
        AliyunConfig oldConfig = iAliyunConfigService.getById(id);
        if (oldConfig==null){
            throw new BizException("目标配置不存在", "The config not exists！");
        }

        boolean ok = iAliyunConfigService.updateById(aliyunConfig);

        return ok?success():fail();

    }

    @Override
    @GetMapping("/{id}")
    protected R get(@PathVariable Long id) throws XException {
        AliyunConfig aliyunConfig = iAliyunConfigService.getById(id);
        if (aliyunConfig==null){
            throw new BizException("目标配置不存在", "The config not exists！");
        }

        return data(aliyunConfig);
    }

    @Override
    @GetMapping("/list")
    protected R list(@RequestParam Integer current, @RequestParam  Integer size) {
        return data(iAliyunConfigService.page(new Page<>(current, size)));
    }
}
