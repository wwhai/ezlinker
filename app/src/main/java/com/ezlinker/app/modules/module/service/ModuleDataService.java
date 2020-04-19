package com.ezlinker.app.modules.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.module.model.ModuleData;
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
 * @date 2020/2/17
 * File description: 模块的☁️原始数据
 */
@Service
public class ModuleDataService {

    @Resource
    MongoTemplate mongoTemplate;

    public void save(ModuleData entity) {
        mongoTemplate.insert(entity, "module_data");
    }

    public IPage<ModuleData> queryForPage(Long moduleId, Pageable pageable) {
        Query query = new Query();
        Criteria criteria = Criteria.where("moduleId").is(moduleId);
        query.fields().include("createTime").include("data");
        query.addCriteria(criteria);

        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.with(pageable);

        List<ModuleData> list = mongoTemplate.find(query, ModuleData.class, "module_data");
        long total = mongoTemplate.count(query, "module_data");

        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }
}
