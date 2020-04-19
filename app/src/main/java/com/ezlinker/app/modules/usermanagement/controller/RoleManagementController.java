package com.ezlinker.app.modules.usermanagement.controller;

import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.role.model.Role;
import com.ezlinker.app.modules.role.service.IRoleService;
import com.ezlinker.app.common.exchange.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: ezlinker
 * @description: 角色管理
 * @author: wangwenhai
 * @create: 2019-12-20 17:06
 **/
@RestController
@RequestMapping("/management/roles")
public class RoleManagementController extends CurdController<Role> {
    @Resource
    IRoleService iRoleService;

    public RoleManagementController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R add(@RequestBody Role role) {
        boolean ok = iRoleService.save(role);
        return ok ? data(role) : fail();
    }

}
