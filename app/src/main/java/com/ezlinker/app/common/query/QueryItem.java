package com.ezlinker.app.common.query;

import lombok.Data;

enum QueryOperator {
    NULL,
    // ==
    EQ,
    // !=
    NEQ,
    // <=
    LTE,
    // >=
    GTE,
    // <
    LT,
    // >
    GT,
    // IN(...)
    IN,
    // Between [A,B]
    BETWEEN;
}

/**
 * @program: ezlinker
 * @description: 查询条件, 其中某些关系必须全部具备才作用
 * @author: wangwenhai
 * @create: 2019-12-20 17:37
 **/
@Data
public class QueryItem {
    // IN的表
    private Object[] in;
    // 当是大小比较的时候，用作运算和被运算l op r;
    // 当是IN的时候，用作边界 [l, r]
    private Object l, r;
    // 操作符
    private QueryOperator op = QueryOperator.NULL;


}
