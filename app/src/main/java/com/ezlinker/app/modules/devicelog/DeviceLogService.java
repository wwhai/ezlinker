package com.ezlinker.app.modules.devicelog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.model.MongoObject;
import com.ezlinker.app.common.web.XPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/9/18
 * File description:
 */
@Service
public class DeviceLogService {
    @Resource
    MongoTemplate mongoTemplate;

    public void save(MongoObject entity) {
        mongoTemplate.insert(entity, "device_log");
    }

    /**
     * @param clientId
     * @param pageable
     * @return
     */
    public IPage<MongoObject> queryForPage(String clientId, Pageable pageable) {
        Query query = new Query();
        long total = mongoTemplate.count(query, "device_log");
        if (!StringUtils.isEmpty(clientId)) {
            Criteria criteria = Criteria.where("clientId").is(clientId);
            query.addCriteria(criteria);
        }
        query.with(pageable);
        List<MongoObject> list = mongoTemplate.find(query, MongoObject.class, "device_log");

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }

}
