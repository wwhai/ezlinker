package com.ezlinker.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;

/**
 * @author wangwenhai
 * @date 2020/9/28
 * File description:
 */
@SpringBootTest
public class MongoTest {
    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void testContext() {
        Query query1 = new Query();
        query1.with(PageRequest.of(1, 10));

        long total1 = mongoTemplate.count(query1, "device_log");
        //
        Query query2 = new Query();
        query2.with(PageRequest.of(0, 10));
        long total2 = mongoTemplate.count(query2, "device_log");

        long total3 = mongoTemplate.count(new Query(), "device_log");

        System.out.println(total1);
        System.out.println(total2);
        System.out.println(total3);
    }

}
