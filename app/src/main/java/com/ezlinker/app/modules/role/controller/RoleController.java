package com.ezlinker.app.modules.role.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.modules.role.model.Role;
import com.ezlinker.app.modules.role.service.IRoleService;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.web.bind.annotation.*;

import com.ezlinker.app.common.web.CurdController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>Title: RoleController</p>
 * <p>Description: com.ezlinker.app.modules.role.controller</p>
 *
 * @author zhaolei
 * @version 1.0.0
 * @date 2019-11-30 16:04
 */
@RestController
@RequestMapping("/role")
public class RoleController extends CurdController<Role> {

    private final IRoleService iRoleService;

    public RoleController(HttpServletRequest httpServletRequest, IRoleService iRoleService) {
        super(httpServletRequest);
        this.iRoleService = iRoleService;
    }

    /**
     * 添加一个角色
     *
     * @param role
     * @return
     */
    @Override
    protected R add(@RequestBody Role role) {
        return iRoleService.save(role) ? success() : fail();
    }

    /**
     * 更新角色
     *
     * @param id
     * @param role
     * @return
     */
    @Override
    protected R update(@PathVariable Long id, @RequestBody Role role) throws XException {
        role.setId(id);
        return iRoleService.updateById(role) ? success() : fail();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) {
        return iRoleService.removeByIds(Arrays.asList(ids)) ? success() : fail();
    }

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    @Override
    protected R get(@PathVariable Long id) {
        return data(iRoleService.getById(id));
    }

    /**
     * 查询列表
     * @param label
     * @param name
     * @param parent
     * @param current
     * @param size
     * @return
     */
    @GetMapping
    public R getRoleList(@RequestParam(value = "label", required = false) String label,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "parent", required = false) Integer parent,
                         @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                         @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>()
                .like(StrUtil.isNotBlank(label), "label", label)
                .like(StrUtil.isNotBlank(name), "name", name)
                .eq(parent != null, "parent", parent)
                .orderByDesc("create_time");
        return data(iRoleService.page(new Page<>(current, size), queryWrapper));
    }
}

