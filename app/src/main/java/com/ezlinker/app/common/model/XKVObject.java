package com.ezlinker.app.common.model;

import java.util.LinkedHashMap;

/**
 * @author wangwenhai
 * @date 2020/8/23
 * File description: K-V Object, X just a pre-char
 */
public class XKVObject extends LinkedHashMap<String, Object> {

    public void addValue(String key, Object value) {
        put(key, value);
    }

    public Object getValue(String key) {
        return get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XKVObject[");
        for (String key : keySet()) {
            sb.append(" ");
            sb.append(key);
            sb.append(" = ");
            sb.append(get(key));
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
