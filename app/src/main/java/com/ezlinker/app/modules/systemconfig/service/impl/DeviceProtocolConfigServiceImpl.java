package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.DeviceProtocolConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.DeviceProtocolConfig;
import com.ezlinker.app.modules.systemconfig.service.IDeviceProtocolConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备协议配置表 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@Service
public class DeviceProtocolConfigServiceImpl extends ServiceImpl<DeviceProtocolConfigMapper, DeviceProtocolConfig> implements IDeviceProtocolConfigService {

}
