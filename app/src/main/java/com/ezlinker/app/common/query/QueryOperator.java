package com.ezlinker.app.common.query;

/**
 * @program: ezlinker
 * @description: QueryOperator
 * @author: wangwenhai
 * @create: 2019-12-20 17:38
 **/
public enum QueryOperator {
    /**
     *
     */
    EQUAL("="),
    /**
     *
     */
    NOT_EQUAL("!="),
    /**
     *
     */
    GREATER_EQUAL(">="),
    /**
     *
     */
    LESS_EQUAL("<="),
    /**
     *
     */
    GREATER(">"),
    /**
     *
     */
    LESS("<"),
    /**
     *
     */
    BETWEEN("BETWEEN"),
    /**
     *
     */
    IN("IN"),
    /**
     *
     */
    LIKE("LIKE");

    private String operator;

    QueryOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
