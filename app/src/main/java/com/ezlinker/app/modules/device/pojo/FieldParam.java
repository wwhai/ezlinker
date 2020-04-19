package com.ezlinker.app.modules.device.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 设备参数,为设备建表使用
 * @create 2019-12-09 21:46
 **/
@Data
public class FieldParam   {
    /**
     * 字段名
     */
    @NotEmpty(message = "字段名不可为空值")
    private String field;
    /**
     * 类型:
     * 1: Number
     * 2: String
     * 3: Boolean
     * 4: JSON Format String
     */
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
