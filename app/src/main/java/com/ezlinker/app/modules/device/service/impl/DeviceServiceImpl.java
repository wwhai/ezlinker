package com.ezlinker.app.modules.device.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.device.mapper.DeviceMapper;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * <p>
 * 实际设备，是产品的一个实例。 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
@Service
@Validated
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {
    @Resource
    DeviceMapper deviceMapper;

    @Override
    public IPage<Device> queryForPage(String sn, String name, String model, String industry, Page<Device> page) {
        return deviceMapper.queryForPage(page, sn, name, model, industry);
    }
}
