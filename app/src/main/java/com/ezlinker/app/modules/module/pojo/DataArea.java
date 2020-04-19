package com.ezlinker.app.modules.module.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 数据域
 * @create 2019-12-07 20:46
 **/
@Data
public class DataArea {
    /**
     * 类型:
     * 1: Number
     * 2: String
     * 3: Boolean
     * 4: JSON Format String
     */
    public static final int NUMBER = 1, STRING = 2, BOOLEAN = 3, JSON = 4;
    /**
     * 字段名
     */
    @NotEmpty(message = "字段名不可为空值")
    private String field;

    @NotEmpty(message = "必须指定字段类型")
    private Integer type;
    /**
     * 字段的默认值,默认为 :空
     */
    private String defaultValue;
    /**
     * 备注
     */
    private String description;
}
