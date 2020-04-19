package com.ezlinker.app.modules.systemconfig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezlinker.app.modules.systemconfig.model.ModuleTypeConfig;

import java.util.List;

/**
 * <p>
 * 模块类型配置表 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
public interface ModuleTypeConfigMapper extends BaseMapper<ModuleTypeConfig> {
    /**
     * 根据协议查模块类型
     * @param protocolTypeId
     * @return
     */
    List<ModuleTypeConfig> all(Long protocolTypeId);
}
