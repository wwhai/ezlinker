package com.ezlinker.app.modules.module.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.modules.module.model.ModuleLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ezlinker
 * @description: ModuleLogService
 * @author: wangwenhai
 * @create: 2019-12-19 16:22
 **/
@Service
public class ModuleLogService {
    @Resource
    MongoOperations mongoOperations;

    /**
     * 保存记录
     *
     * @param moduleLog
     */
    public void save(ModuleLog moduleLog) {
        mongoOperations.save(moduleLog, "module_log");
    }

    /**
     * 列表查询
     *
     * @param moduleId
     * @param pageable
     * @return
     */
    public IPage<ModuleLog> queryForPage(Long moduleId, Pageable pageable) {
        Query query = new Query();
        if (moduleId != null) {
            query.addCriteria(Criteria.where("moduleId").is(moduleId));
        }

        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);

        List<ModuleLog> list = mongoOperations.find(query, ModuleLog.class, "module_log");
        long total = mongoOperations.count(query, "module_log");

        return new IPage<ModuleLog>() {
            @Override
            public List<OrderItem> orders() {
                return OrderItem.descs("_id");
            }

            @Override
            public List<ModuleLog> getRecords() {
                return list;
            }

            @Override
            public IPage<ModuleLog> setRecords(List<ModuleLog> records) {
                return this;
            }

            @Override
            public long getTotal() {
                return total;
            }

            @Override
            public IPage<ModuleLog> setTotal(long total) {
                return this;
            }

            @Override
            public long getSize() {
                return pageable.getPageSize();
            }

            @Override
            public IPage<ModuleLog> setSize(long size) {
                return this;
            }

            @Override
            public long getCurrent() {
                return pageable.getPageNumber();
            }

            @Override
            public IPage<ModuleLog> setCurrent(long current) {
                return this;
            }
        };
    }

}
