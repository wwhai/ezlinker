package com.ezlinker.app.modules.device.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ezlinker.app.common.model.MongoObject;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>
 * 实际设备，是产品的一个实例。 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
public interface IDeviceService extends IService<Device> {
    /**
     * @param sn
     * @param name
     * @param projectId
     * @param productId
     * @param model
     * @param industry
     * @param page
     * @return
     */
    IPage<Device> queryForPage(String sn,
                               String name,
                               Long projectId,
                               Long productId,
                               String model,
                               String industry,
                               Page<Device> page);

    /**
     * @param clientId
     * @param fields
     * @return
     */

    List<LinkedHashMap<String, Object>> getLastState(String clientId, String... fields);

    /**
     * @param clientId
     * @param field
     * @param value
     */
    void updateState(String clientId, String field, Object value);

    /**
     * 获取历史状态
     *
     * @param clientId
     * @param fields
     * @return
     */
    IPage<MongoObject> getHistoryState(String clientId, String[] fields, Pageable pageable);

    /**
     * 保存历史状态
     *
     * @param record
     * @param clientId
     */
    void saveHistoryState(MongoObject record, String clientId);

    /**
     * @param device
     */
    void initState(Device device);

    /**
     * @param clientId
     * @return
     */
    List<FieldParam> getFieldParams(String clientId);

}
