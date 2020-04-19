package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.ModuleTypeConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.ModuleTypeConfig;
import com.ezlinker.app.modules.systemconfig.service.IModuleTypeConfigService;
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
public class ModuleTypeConfigServiceImpl extends ServiceImpl<ModuleTypeConfigMapper, ModuleTypeConfig> implements IModuleTypeConfigService {

    @Resource
    ModuleTypeConfigMapper moduleTypeConfigMapper;

    @Override
    public List<ModuleTypeConfig> all(Long protocolTypeId) {
        return moduleTypeConfigMapper.all(protocolTypeId);
    }
}
