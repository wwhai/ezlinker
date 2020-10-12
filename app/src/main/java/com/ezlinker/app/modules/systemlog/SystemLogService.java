package com.ezlinker.app.modules.devicelog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.systemlog.model.SystemLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/9/18
 * File description:
 */
@Service
public class SystemLogService {
    @Resource
    MongoTemplate mongoTemplate;

    public void save(SystemLog entity) {
        mongoTemplate.insert(entity, MongoCollectionPrefix.SYSTEM_INTERNAL_LOG);
    }

    public IPage<SystemLog> queryForPage(Pageable pageable) {
        Query query = new Query();
        long total = mongoTemplate.count(query, MongoCollectionPrefix.SYSTEM_INTERNAL_LOG);

        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);

        List<SystemLog> list = mongoTemplate.find(query, SystemLog.class, MongoCollectionPrefix.SYSTEM_INTERNAL_LOG);

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }
}
