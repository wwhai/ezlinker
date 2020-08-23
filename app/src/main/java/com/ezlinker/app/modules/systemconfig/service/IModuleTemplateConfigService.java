package com.ezlinker.app.modules.systemconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezlinker.app.modules.systemconfig.model.ModuleTemplateConfig;

import java.util.List;

/**
 * <p>
 * 模块类型配置表 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
public interface IModuleTemplateConfigService extends IService<ModuleTemplateConfig> {
    /**
     * 根据协议查模块类型
     *
     * @param protocolTypeId
     * @return
     */
    List<ModuleTemplateConfig> all(Long protocolTypeId);
}
