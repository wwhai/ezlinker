package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.IconConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.IconConfig;
import com.ezlinker.app.modules.systemconfig.service.IIconConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后端维持的图表库配置表 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@Service
public class IconConfigServiceImpl extends ServiceImpl<IconConfigMapper, IconConfig> implements IIconConfigService {

}
