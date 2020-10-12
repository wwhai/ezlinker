package com.ezlinker.app.modules.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.modules.device.model.Device;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 实际设备，是产品的一个实例。 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * @param page
     * @param sn
     * @param name
     * @param projectId
     * @param productId
     * @param model
     * @param industry
     * @return
     */
    IPage<Device> queryForPage(Page<Device> page,
                               @Param("sn") String sn,
                               @Param("name") String name,
                               @Param("projectId") Long projectId,
                               @Param("productId") Long productId,
                               @Param("model") String model,
                               @Param("industry") String industry);


    /**
     * @param clientId
     * @return
     */
    String getFieldParams(@Param("clientId") String clientId);

}
