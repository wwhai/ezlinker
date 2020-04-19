package com.ezlinker.app.common.auth;

import java.lang.annotation.*;

/**
 * @program: ezlinker
 * @description: 角色拦截
 * @author: wangwenhai
 * @create: 2019-11-07 09:06
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {
    String[] roles() default {};
}
