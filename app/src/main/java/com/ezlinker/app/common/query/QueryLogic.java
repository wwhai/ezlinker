package com.ezlinker.app.common.query;

/**
 * @program: ezlinker
 * @description:
 * @author: wangwenhai
 * @create: 2019-12-20 17:38
 **/
public enum QueryLogic {
    /**
     *  无逻辑
     */
    NONE("NONE"),
    /**
     * AND 查询
     */
    AND("AND"),
    /**
     * OR 查询
     */
    OR("OR");

    private String operator;

    QueryLogic(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
