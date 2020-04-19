package com.ezlinker.app.modules.internalmessage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.modules.internalmessage.model.InternalMessage;
import com.ezlinker.app.modules.internalmessage.service.InternalMessageService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lc5900
 * @since 2019-11-13
 */
@Service
public class InternalMessageServiceImpl implements InternalMessageService {


    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void save(InternalMessage entity) {
        mongoTemplate.insert(entity, "internal_message");
    }

    @Override
    public void removeByIds(Integer[] ids) {
        Query query = new Query();
        mongoTemplate.remove(query.addCriteria(Criteria.where("_id").in(Arrays.asList(ids))), "internal_message");
    }


    @Override
    public void mark(String id) {
        Query query = new Query();
        Update update = new Update();
        update.set("marked", 1);
        mongoTemplate.updateFirst(query.addCriteria(Criteria.where("_id").is(new ObjectId(id))), update, "internal_message");
    }

    @Override
    public InternalMessage getOne(String id) {
        Query query = new Query();
        return mongoTemplate.findOne(query.addCriteria(Criteria.where("_id").is(new ObjectId(id))), InternalMessage.class, "internal_message");
    }

    @Override
    public IPage<InternalMessage> queryForPage(Long userId, Integer marked, Pageable pageable) {
        Query query = new Query();
//        Sort sort = new Sort(Sort.Direction.ASC, "");

        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("marked").is(marked));
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        query.with(pageable);

        List<InternalMessage> list = mongoTemplate.find(query, InternalMessage.class, "internal_message");
        long total = mongoTemplate.count(query, "internal_message");

        return new IPage<InternalMessage>() {
            @Override
            public List<OrderItem> orders() {
                return OrderItem.descs("createTime");
            }

            @Override
            public List<InternalMessage> getRecords() {
                return list;
            }

            @Override
            public IPage<InternalMessage> setRecords(List<InternalMessage> records) {
                return this;
            }

            @Override
            public long getTotal() {
                return total;
            }

            @Override
            public IPage<InternalMessage> setTotal(long total) {
                return this;
            }

            @Override
            public long getSize() {
                return pageable.getPageSize();
            }

            @Override
            public IPage<InternalMessage> setSize(long size) {
                return this;
            }

            @Override
            public long getCurrent() {
                return pageable.getPageNumber();
            }

            @Override
            public IPage<InternalMessage> setCurrent(long current) {
                return this;
            }
        };
    }

    @Override
    public long count(Long userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("marked").is(0L));
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        return mongoTemplate.count(query, "internal_message");
    }
}
