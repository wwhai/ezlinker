package com.ezlinker.app.modules.device.service.impl;

import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.mapper.DeviceMapper;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实际设备，是产品的一个实例。 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

}
