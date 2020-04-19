package com.ezlinker.app.modules.module.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.dataentry.model.DeviceData;
import com.ezlinker.app.modules.dataentry.service.DeviceDataService;
import com.ezlinker.app.modules.module.model.Module;
import com.ezlinker.app.modules.module.service.IModuleService;
import com.ezlinker.app.modules.module.service.ModuleDataService;
import com.ezlinker.app.modules.module.service.ModuleLogService;
import com.ezlinker.app.modules.systemconfig.service.IModuleTypeConfigService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
    DeviceDataService deviceDataService;

    @Resource
    IModuleTypeConfigService iModuleTypeConfigService;

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
        return data(iModuleTypeConfigService.list());
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
        Pageable pageable = PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "id"));
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
     * 获取状态
     * 设备数据表：device_data_{projectId}_{deviceId}
     *
     * @return
     * @throws XException
     */
    @GetMapping("/{moduleIds}/{projectId}/status")
    public R status(@PathVariable Long[] moduleIds, @PathVariable Long projectId) throws XException {
        HashMap<Long, HashMap<String, Object>> map = new HashMap<>();
        for (Long id : moduleIds) {

            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "_id"));
            IPage<DeviceData> page = deviceDataService.queryForPage(projectId, id, pageable);
            if (page.getRecords().size() > 0) {
                DeviceData deviceData = deviceDataService.queryForPage(projectId, id, pageable).getRecords().get(0);
                HashMap<String, Object> status = new HashMap<>();
                status.put("status", deviceData.getData());
                status.put("createTime", deviceData.getCreateTime());
                map.put(id, status);
            } else {
                map.put(id, null);

            }

        }
        return data(map);
    }

}
