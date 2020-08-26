package com.ezlinker.app.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 填充器
 *
 * @author wangwenhai
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Object updateTime = this.getFieldValByName("createTime", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("createTime", now, metaObject);
        }
        Object recordVersion = this.getFieldValByName("recordVersion", metaObject);
        if (null == recordVersion) {
            this.setFieldValByName("recordVersion", 0L, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
