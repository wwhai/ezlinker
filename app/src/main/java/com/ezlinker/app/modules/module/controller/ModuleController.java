package com.ezlinker.app.modules.module.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.module.model.Module;
import com.ezlinker.app.modules.module.service.IModuleService;
import com.ezlinker.app.modules.module.service.ModuleDataService;
import com.ezlinker.app.modules.module.service.ModuleLogService;
import com.ezlinker.app.modules.systemconfig.service.IModuleTemplateConfigService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 设备上面的模块，和设备是多对一关系
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/modules")
public class ModuleController extends CurdController<Module> {
    @Resource
    ModuleLogService moduleLogService;

    @Resource
    IModuleService iModuleService;

    @Resource
    ModuleDataService moduleDataService;

    @Resource
    IModuleTemplateConfigService iModuleTemplateConfigService;

    public ModuleController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    /**
     * 获取当前支持的设备的类型
     * 设备类型用来处理UI上显示效果
     * 0 自定义类型
     * 1 按钮
     * 2 按钮组
     * 3 开关
     * 4 开关组
     * 5 进度条
     * 6 图表
     *
     * @return
     */
    @GetMapping("/types")
    public R getType() {
        return data(iModuleTemplateConfigService.list());
    }


    /**
     * 获取详情
     *
     * @param id
     * @return
     * @throws XException
     */
    @Override
    protected R get(@PathVariable Long id) throws XException {
        Module module = iModuleService.getById(id);
        if (module == null) {
            throw new BizException("Component not exists", "模块不存在");

        }
        return data(module);
    }


    /**
     * 上下线日志
     *
     * @param current
     * @param size
     * @param moduleId
     * @return
     */
    @GetMapping("/logs")
    public R logs(@RequestParam Integer current, @RequestParam Integer size, @RequestParam(required = false) Long moduleId) {
        Pageable pageable = PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "_id"));
        return data(moduleLogService.queryForPage(moduleId, pageable));
    }


    /**
     * 获取数据
     *
     * @return
     * @throws XException
     */
    @GetMapping("/{moduleId}/data")
    public R queryForPage(@PathVariable Long moduleId, @RequestParam Integer current, @RequestParam Integer size) throws XException {
        Pageable pageable = PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "id"));
        Module module = iModuleService.getById(moduleId);
        if (module == null) {
            throw new BizException("Device not exist", "设备不存在");
        }

        return data(moduleDataService.queryForPage(moduleId, pageable));
    }

    /**
     * 获取设备的模块列表
     *
     * @param current
     * @param size
     * @param deviceId
     * @return
     */
    @GetMapping
    public R queryForPage(
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false) Long deviceId) {
        IPage<Module> moduleIPage = iModuleService.page(new Page<>(current, size), new QueryWrapper<Module>().eq(deviceId != null, "device_id", deviceId));
        return data(moduleIPage);
    }
}
