package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.ModuleTemplateConfig;
import com.ezlinker.app.modules.systemconfig.service.IModuleTemplateConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 模块类型配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@RestController
@RequestMapping({"/systemConfig/moduleType", "/systemConfig/moduleTemplate"})
public class ModuleTemplateConfigController extends CurdController<ModuleTemplateConfig> {

    @Resource
    IModuleTemplateConfigService iModuleTemplateConfigService;

    public ModuleTemplateConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 列出所有
     *
     * @return
     */

    @Override
    protected R all() {
        return data(iModuleTemplateConfigService.list());
    }

    /**
     * @param protocolId
     * @return
     */
    @GetMapping
    public R queryForPage(@RequestParam Long protocolId) {

        return data(iModuleTemplateConfigService.all(protocolId));
    }

}

