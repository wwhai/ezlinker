package com.ezlinker.app.modules.internalmsg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.internalmsg.model.InternalMessage;
import com.ezlinker.app.modules.internalmsg.service.InternalMessageService;
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
        long total = mongoTemplate.count(query, "internal_message");

//        Sort sort = new Sort(Sort.Direction.ASC, "");

        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("marked").is(marked));
        query.with(Sort.by(Sort.Direction.DESC, "createTime"));
        query.with(pageable);

        List<InternalMessage> list = mongoTemplate.find(query, InternalMessage.class, "internal_message");
        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
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
