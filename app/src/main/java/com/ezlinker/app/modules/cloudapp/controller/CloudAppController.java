package com.ezlinker.app.modules.cloudapp.controller;


import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.cloudapp.model.CloudApp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 云APP
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/cloudApps")
public class CloudAppController extends CurdController<CloudApp> {

    public CloudAppController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }
}

