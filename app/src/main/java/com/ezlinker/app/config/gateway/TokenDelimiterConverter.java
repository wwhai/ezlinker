package com.ezlinker.app.config.gateway;

/**
 * @author wangwenhai
 * @date 2020/9/27
 * File description: 简单字符串分隔符数据格式处理器
 */
public class TokenDelimiterConverter implements IFormatConverter<String[]> {
    private char delimiter;
    private String payload;

    TokenDelimiterConverter(String payload) {
        // 第一个是固定：C，第二个是分隔符char
        this.delimiter = payload.charAt(1);
        this.payload = payload;
    }

    @Override
    public String[] handler() {
        //C,[2,3,4,5,6,7]
        if (payload != null && payload.length() > 2) {
            return payload.substring(2).split(String.valueOf(delimiter));
        } else {
            return new String[]{};
        }
    }
}
