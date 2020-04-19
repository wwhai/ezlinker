package com.ezlinker.app.modules.module.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 数据值
 * @create 2019-12-16 22:37
 **/
@Data
public class DataAreaValue {
    private String field;
    private Object value;
}
