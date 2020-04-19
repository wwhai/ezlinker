package com.ezlinker.app.modules.permission.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.modules.permission.model.Permission;
import com.ezlinker.app.modules.permission.service.IPermissionService;
import com.ezlinker.app.common.exchange.R;
import org.springframework.web.bind.annotation.*;

import com.ezlinker.app.common.web.CurdController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>Title: PermissionController</p>
 * <p>Description: com.ezlinker.app.modules.permission.controller</p>
 *
 * @author zhaolei
 * @version 1.0.0
 * @date 2019-11-30 16:09
 */
@RestController
@RequestMapping("/permissions")
public class PermissionController extends CurdController<Permission> {

    private final IPermissionService iPermissionService;

    public PermissionController(HttpServletRequest httpServletRequest, IPermissionService iPermissionService) {
        super(httpServletRequest);
        this.iPermissionService = iPermissionService;
    }

    /**
     * 添加一个T
     *
     * @param permission
     * @return
     */
    @Override
    protected R add(@RequestBody Permission permission) {
        return iPermissionService.save(permission) ? success() : fail();
    }

    /**
     * 更新T
     *
     * @param id
     * @param permission
     * @return
     */
    @Override
    protected R update(@PathVariable Long id, @RequestBody Permission permission) {
        permission.setId(id);
        return iPermissionService.updateById(permission) ? success() : fail();
    }

    /**
     * 批量删除T
     *
     * @param ids
     * @return
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) {
        return iPermissionService.removeByIds(Arrays.asList(ids))?success():fail();
    }

    /**
     * 查询单个T
     *
     * @param id
     * @return
     */
    @Override
    protected R get(@PathVariable Long id) {
        return data(iPermissionService.getById(id));
    }

    /**
     * 查询列表
     * @param label
     * @param name
     * @param resource
     * @param current
     * @param size
     * @return
     */
    @GetMapping
    public R getPermissionList(@RequestParam(value = "label", required = false) String label,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "resource", required = false) String resource,
                               @RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>()
                .like(StrUtil.isNotBlank(label), "label", label)
                .like(StrUtil.isNotBlank(name), "name", name)
                .like(StrUtil.isNotBlank(resource), "resource", resource)
                .orderByDesc("create_time");
        return data(iPermissionService.page(new Page<>(current, size), queryWrapper));
    }
}

