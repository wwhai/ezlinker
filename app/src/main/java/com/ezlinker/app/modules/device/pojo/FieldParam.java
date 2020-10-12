package com.ezlinker.app.modules.device.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 设备参数, 为设备建表使用
 * @create 2019-12-09 21:46
 **/
@Data
public class FieldParam implements Serializable {
    /**
     * 字段名
     */
    @NotEmpty(message = "字段名不可为空值")
    private String field;
    /**
     * 计量单位，比如Kg M N等等
     * 前端通过下拉列表来选择
     * 【计量单位】
     * ----千克
     * ----米
     * ----千瓦
     * ----牛顿
     * <p>
     * 默认为空，前端显示为Value
     */
    private String unit = null;
    /**
     * 类型
     */
    @NotEmpty(message = "必须指定字段类型")
    private FieldType fieldType = FieldType.NUMBER;
    /**
     * 字段的默认值,默认为 :空
     */
    private Object defaultValue = null;
    /**
     * 运行状态的值, 默认是null,只有在运行的时候才会提供给前端
     */
    private Object value = null;

    public Object getDefaultValue() {

        if (defaultValue == null) {
            switch (getFieldType()) {
                case NUMBER:
                    return 0;
                case TEXT:
                    return "NULL";
                case BOOLEAN:
                    return false;
                // TODO:测试值 ,后期找一个推流提供商
                case STREAM:
                    // 留给后台配置推流地址,这里暂时给个规范: ${地址}?token=#{token}
                    return "http://demo.easynvr.com:10800/hls/stream_47/stream_47_live.m3u8?token=t7BhgNp4";
                case GEO:
                    return "0,0";
                default:
                    return null;
            }
        } else {
            return defaultValue;
        }
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
    /**
     * 备注
     */
    private String description;
}
