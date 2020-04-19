package com.ezlinker.app.modules.permission.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户权限
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "ez_permission", autoResultMap = true)
public class Permission extends XEntity {

    /**
     * 权限资源的类型
     * 1 目录
     * 2 接口
     */
    public static final int MENU = 1;
    public static final int ACTION = 2;

    private static final long serialVersionUID = 1L;

    private String label;

    private String name;

    private String resource;

    private Integer type;

    private Integer parent;

    private String description;

    /**
     * NO - 0
     * C - 1
     * CU - 3
     * CUR - 6
     * CURD - 10
     */
    public static int C = 1;
    public static int U = 2;
    public static int R = 3;
    public static int D = 4;

    private Integer permissionValue = 0;

}
