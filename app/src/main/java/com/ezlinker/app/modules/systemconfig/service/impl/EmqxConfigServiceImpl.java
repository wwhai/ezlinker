package com.ezlinker.app.modules.systemconfig.service.impl;

import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import com.ezlinker.app.modules.systemconfig.mapper.EmqxConfigMapper;
import com.ezlinker.app.modules.systemconfig.service.IEmqxConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * EMQX配置表 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-06
 */
@Service
public class EmqxConfigServiceImpl extends ServiceImpl<EmqxConfigMapper, EmqxConfig> implements IEmqxConfigService {

}
