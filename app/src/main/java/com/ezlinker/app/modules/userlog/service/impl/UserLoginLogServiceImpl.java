package com.ezlinker.app.modules.userlog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ezlinker.app.common.web.XPage;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.userlog.model.UserLoginLog;
import com.ezlinker.app.modules.userlog.service.IUserLoginLogService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-12
 */
@Service
public class UserLoginLogServiceImpl implements IUserLoginLogService<UserLoginLog> {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void save(UserLoginLog entity) {
        mongoTemplate.insert(entity, MongoCollectionPrefix.USER_LOGIN_LOG);
    }

    @Override
    public IPage<UserLoginLog> queryForPage(Long userId, Pageable pageable) {
        Query query = new Query();
        long total = mongoTemplate.count(query, MongoCollectionPrefix.USER_LOGIN_LOG);
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.with(pageable);
        List<UserLoginLog> list = mongoTemplate.find(query, UserLoginLog.class, MongoCollectionPrefix.USER_LOGIN_LOG);
        return new XPage<>(list, total, OrderItem.descs("_id"), pageable);
    }
}
