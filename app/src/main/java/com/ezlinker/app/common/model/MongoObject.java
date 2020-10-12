package com.ezlinker.app.common.model;

import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangwenhai
 * @date 2020/9/27
 * File description: 新增Mongodb存储对象类
 */
public class MongoObject extends Document {
    public MongoObject() {
        this.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public MongoObject with(String key, Object value) {
        this.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return this.toJson();
    }
}
