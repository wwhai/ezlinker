package com.ezlinker.app.config.gateway;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wangwenhai
 * @date 2020/10/8
 * File description: JSON格式的数据处理器
 */
public class JsonConverter implements IFormatConverter<JSONObject> {

    private String payload;

    JsonConverter(String payload) {
        this.payload = payload;
    }

    @Override
    public JSONObject handler() {
        return JSONObject.parseObject(payload);
    }
}
