package com.ezlinker.app.modules.user.model;

import lombok.Data;

import java.util.List;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 权限
 * @create 2019-12-03 21:06
 **/
@Data
public class PermissionDetail {
    /**
     * `资源权限
     */
    private List<String> methods;
    /**
     * 用户权限
     */
    private List<String> allow;
    /**
     * 路径
     */
    private String resource;
}
