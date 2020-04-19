package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.DeviceProtocolConfig;
import com.ezlinker.app.modules.systemconfig.service.IDeviceProtocolConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 设备协议配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/systemConfig/protocols")
public class DeviceProtocolConfigController extends CurdController<DeviceProtocolConfig> {

    public DeviceProtocolConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    @Resource
    IDeviceProtocolConfigService iDeviceProtocolConfigService;

    @Override
    protected R all() {
        return data(iDeviceProtocolConfigService.list());
    }
}

