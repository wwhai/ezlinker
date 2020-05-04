package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.DeviceConfigExportFormatConfig;
import com.ezlinker.app.modules.systemconfig.service.IDeviceConfigExportFormatConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 导出配置的格式，比如XML或者INI等等 前端控制器
 * </p>
 *
 * @author wangwenhai
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/systemConfig/dcefc")//避免URL过长dcefc是简写
public class DeviceConfigExportFormatConfigController extends CurdController<DeviceConfigExportFormatConfig> {

    @Resource
    IDeviceConfigExportFormatConfigService iDeviceConfigExportFormatConfigService;

    public DeviceConfigExportFormatConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R all() throws Exception {
        return data(iDeviceConfigExportFormatConfigService.list());
    }
}

