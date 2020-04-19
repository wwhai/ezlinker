package com.ezlinker.app.modules.authorize.controller;


import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.authorize.model.ResourceAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 资源授权表,记录所有存在权限关系的双方
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@RestController
@RequestMapping("/authorizes")
public class ResourceAuthorizeController extends CurdController<ResourceAuthorize> {

    public ResourceAuthorizeController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }
}

