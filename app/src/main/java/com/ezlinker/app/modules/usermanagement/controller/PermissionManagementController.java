package com.ezlinker.app.modules.usermanagement.controller;

import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.permission.model.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ezlinker
 * @description: 权限管理
 * @author: wangwenhai
 * @create: 2019-12-20 17:06
 **/
@RestController
@RequestMapping("/management/permissions")
public class PermissionManagementController extends CurdController<Permission> {
    public PermissionManagementController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }
}
