package com.ezlinker.app.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import javax.annotation.Resource;

/**
 * @program: ezlinker
 * @description: MongoDB配置
 * @author: wangwenhai
 * @create: 2019-12-13 15:07
 **/
@Configuration
public class MongoDbConfig implements InitializingBean {

    @Resource
    @Lazy
    private MappingMongoConverter mappingMongoConverter;

    @Override
    public void afterPropertiesSet() {
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }
}
