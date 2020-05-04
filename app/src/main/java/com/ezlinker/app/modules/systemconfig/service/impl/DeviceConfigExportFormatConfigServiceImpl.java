package com.ezlinker.app.modules.systemconfig.service.impl;

import com.ezlinker.app.modules.systemconfig.model.DeviceConfigExportFormatConfig;
import com.ezlinker.app.modules.systemconfig.mapper.DeviceConfigExportFormatConfigMapper;
import com.ezlinker.app.modules.systemconfig.service.IDeviceConfigExportFormatConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 导出配置的格式，比如XML或者INI等等 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-05-04
 */
@Service
public class DeviceConfigExportFormatConfigServiceImpl extends ServiceImpl<DeviceConfigExportFormatConfigMapper, DeviceConfigExportFormatConfig> implements IDeviceConfigExportFormatConfigService {

}
