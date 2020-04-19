package com.ezlinker.app.modules.shareddevice.controller;


import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.shareddevice.model.SharedDevice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 共享设备记录表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-11
 */
@RestController
@RequestMapping("/sharedDevices")
public class SharedDeviceController extends CurdController<SharedDevice> {

    public SharedDeviceController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }
}

