package com.ezlinker.app.modules.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.modules.device.model.Device;

/**
 * <p>
 * 实际设备，是产品的一个实例。 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
public interface DeviceMapper extends BaseMapper<Device> {

    IPage<Device> queryForPage(Page<Device> page, String sn, String name, String model, String industry);
}
