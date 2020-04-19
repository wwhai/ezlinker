package com.ezlinker.app.modules.userlog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
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
        mongoTemplate.insert(entity, "user_login_log");
    }

    @Override
    public IPage<UserLoginLog> queryForPage(Long userId, Pageable pageable) {
        Query query = new Query();
        Criteria criteria = Criteria.where("userId").is(userId);
        query.addCriteria(criteria);

        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.with(pageable);

        List<UserLoginLog> list = mongoTemplate.find(query, UserLoginLog.class, "user_login_log");
        long total = mongoTemplate.count(query, "user_login_log");

        return new IPage<UserLoginLog>() {
            @Override
            public List<OrderItem> orders() {
                return OrderItem.descs("id");
            }

            @Override
            public List<UserLoginLog> getRecords() {
                return list;
            }

            @Override
            public IPage<UserLoginLog> setRecords(List<UserLoginLog> records) {
                return this;
            }

            @Override
            public long getTotal() {
                return total;
            }

            @Override
            public IPage<UserLoginLog> setTotal(long total) {
                return this;
            }

            @Override
            public long getSize() {
                return pageable.getPageSize();
            }

            @Override
            public IPage<UserLoginLog> setSize(long size) {
                return this;
            }

            @Override
            public long getCurrent() {
                return pageable.getPageNumber();
            }

            @Override
            public IPage<UserLoginLog> setCurrent(long current) {
                return this;
            }
        };
    }
}
