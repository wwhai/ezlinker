package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.ModuleTemplateConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.ModuleTemplateConfig;
import com.ezlinker.app.modules.systemconfig.service.IModuleTemplateConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 模块类型配置表 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@Service
public class ModuleTypeConfigServiceImpl extends ServiceImpl<ModuleTemplateConfigMapper, ModuleTemplateConfig> implements IModuleTemplateConfigService {

    @Resource
    ModuleTemplateConfigMapper moduleTypeConfigMapper;

    @Override
    public List<ModuleTemplateConfig> all(Long protocolTypeId) {
        return moduleTypeConfigMapper.all(protocolTypeId);
    }
}
