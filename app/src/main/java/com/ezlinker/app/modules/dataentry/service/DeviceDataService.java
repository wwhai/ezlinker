package com.ezlinker.app.modules.dataentry.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.dataentry.model.DeviceData;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/2/16
 * File description: 设备数据service
 * 备注：Mongodb集合是动态生成的，规则：device_data_{projectId}_{deviceId}
 */
@Service
public class DeviceDataService {
    @Resource
    MongoTemplate mongoTemplate;

    public void save(Long projectId, Long deviceId, DeviceData entity) {
        mongoTemplate.insert(entity, "device_data_" + projectId + "_" + deviceId);
    }

    public IPage<DeviceData> queryForPage(Long projectId, Long deviceId, org.springframework.data.domain.Pageable pageable) {
        Query query = new Query();
        Criteria criteria = Criteria.where("deviceId").is(deviceId);
        query.fields().include("createTime").include("data");
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);
        List<DeviceData> list = mongoTemplate.find(query, DeviceData.class, "device_data_" + projectId + "_" + deviceId);
        long total = mongoTemplate.count(query, "device_data_" + projectId + "_" + deviceId);

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }
}
