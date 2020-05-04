package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.DeviceRomSupportConfig;
import com.ezlinker.app.modules.systemconfig.service.IDeviceRomSupportConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 支持的固件 前端控制器
 * </p>
 *
 * @author wangwenhai
 * @since 2020-05-04
 */
@RestController
@RequestMapping("/systemConfig/drsc")//避免URL过长drsc是简写
public class DeviceRomSupportConfigController extends CurdController<DeviceRomSupportConfig> {

    @Resource
    IDeviceRomSupportConfigService iDeviceRomSupportConfigService;

    public DeviceRomSupportConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R all() throws Exception {
        return data(iDeviceRomSupportConfigService.list());
    }
}

