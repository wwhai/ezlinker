package com.ezlinker.app.common.auth;

import java.lang.annotation.*;

/**
 * @program: ezlinker
 * @description: 要求认证
 * @author: wangwenhai
 * @create: 2019-11-07 09:10
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireAuth {
    boolean required() default false;
}
