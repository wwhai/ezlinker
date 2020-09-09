package com.ezlinker.app.modules.device.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezlinker.app.modules.device.model.Device;

/**
 * <p>
 * 实际设备，是产品的一个实例。 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
public interface IDeviceService extends IService<Device> {
     IPage<Device> queryForPage(String sn, String name, String model, String industry, Page<Device> page);

}
