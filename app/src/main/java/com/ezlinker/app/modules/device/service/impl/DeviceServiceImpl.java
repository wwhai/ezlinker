package com.ezlinker.app.modules.device.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.common.model.MongoObject;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.device.mapper.DeviceMapper;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
    @Resource
    RedisUtil redisUtil;
    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 类型转换
     *
     * @param o
     * @return
     */
    private static List<FieldParam> covertMapToFieldParams(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(o, new TypeReference<List<FieldParam>>() {
        });
    }

    /**
     * 初始化状态
     *
     * @param device
     */
    public void initState(Device device) {

        for (FieldParam fieldParam : covertMapToFieldParams(device.getFieldParams())) {
            redisUtil.hset(RedisKeyPrefix.DEVICE_RUNNING_STATE + device.getId(), fieldParam.getField(), fieldParam.getDefaultValue());
        }
    }

    @Override
    public List<FieldParam> getFieldParams(String clientId) {
        return JSONArray.parseArray(deviceMapper.getFieldParams(clientId), FieldParam.class);
    }


    /**
     * 更新状态到redis
     *
     * @param field 需要更新的字段
     * @param value 新值
     */
    @Override
    public void updateState(String clientId, String field, Object value) {
        if (redisUtil.hHasKey(clientId, field)) {
            redisUtil.hset(RedisKeyPrefix.DEVICE_RUNNING_STATE + clientId, field, value);
        }
    }

    /**
     * ¬
     *
     * @param entity
     * @param clientId
     */
    public void saveHistoryState(MongoObject entity, String clientId) {
        mongoTemplate.insert(entity, MongoCollectionPrefix.DEVICE_HISTORY_STATE + clientId);

    }

    /**
     * 获取历史状态记录
     * 保存在Mongodb
     *
     * @param clientId 设备ID
     * @param fields   字段列表:[k1, k2, k3....kn]
     * @return
     */
    @Override
    public IPage<MongoObject> getHistoryState(String clientId, String[] fields, Pageable pageable) {
        Query query = new Query();
        long total = mongoTemplate.count(query, MongoCollectionPrefix.DEVICE_HISTORY_STATE + clientId);
        Criteria criteria = Criteria.where("clientId").is(clientId);
        query.addCriteria(criteria);
        query.fields().exclude("_id");
        query.fields().exclude("clientId");
        // 有列限制才include
        if (fields.length > 0) {
            for (String field : fields) {
                query.fields().include(field);
            }
        }
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);
        List<MongoObject> list = mongoTemplate.find(query, MongoObject.class, MongoCollectionPrefix.DEVICE_HISTORY_STATE + clientId);

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }

    @Override
    public IPage<Device> queryForPage(String sn, String name, Long projectId, Long productId, String model, String industry, Page<Device> page) {
        return deviceMapper.queryForPage(page, sn, name, projectId, productId, model, industry);
    }

    /**
     * 获取设备最后一次的某个状态值，如果从未被设置则为null
     * IMPORTANT: 永远获取到的是最后一次更新的状态
     *
     * @param clientId 设备ID
     * @param fields   要获取的字段名称
     * @return
     */
    public List<LinkedHashMap<String, Object>> getLastState(String clientId, String... fields) {
        List<LinkedHashMap<String, Object>> list = new ArrayList<>();
        if (clientId != null && fields.length > 0) {
            for (String field : fields) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                Object value = redisUtil.hget(RedisKeyPrefix.DEVICE_RUNNING_STATE + clientId, field);
                map.put("field", field);
                if (value != null) {
                    map.put("value", value);
                } else {
                    map.put("value", null);
                }
                list.add(map);
            }
        }
        return list;
    }
}
