package com.ezlinker.app.modules.dataentry.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.dataentry.model.DeviceData;
import org.springframework.data.domain.Pageable;
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
 * 备注：Mongodb集合是动态生成的，规则：device_history_data_{clientId}
 */
@Service
public class DeviceDataService {
    @Resource
    MongoTemplate mongoTemplate;

    public void save(String clientId, DeviceData entity) {
        mongoTemplate.insert(entity, MongoCollectionPrefix.DEVICE_HISTORY_DATA + clientId);
    }

    public IPage<DeviceData> queryForPage(String clientId, Pageable pageable) {
        Query query = new Query();
        long total = mongoTemplate.count(query, MongoCollectionPrefix.DEVICE_HISTORY_DATA + clientId);
        Criteria criteria = Criteria.where("clientId").is(clientId);
        //query.fields().include("createTime").include("data");
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);
        List<DeviceData> list = mongoTemplate.find(query, DeviceData.class, MongoCollectionPrefix.DEVICE_HISTORY_DATA + clientId);

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }
}
