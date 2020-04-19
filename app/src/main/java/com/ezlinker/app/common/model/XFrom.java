package com.ezlinker.app.common.model;

import lombok.Data;

/**
 * @program: ezlinker
 * @description: 表单抽象类
 * @author: wangwenhai
 * @create: 2019-11-11 11:08
 **/
@Data
public class XFrom<T> {
    private T data;
}
