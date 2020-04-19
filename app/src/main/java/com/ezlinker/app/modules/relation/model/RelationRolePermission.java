package com.ezlinker.app.modules.relation.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色和权限关联表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_relation_role_permission")
public class RelationRolePermission extends XEntity {

    private static final long serialVersionUID=1L;

    private Long roleId;

    private Long permissionId;

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
