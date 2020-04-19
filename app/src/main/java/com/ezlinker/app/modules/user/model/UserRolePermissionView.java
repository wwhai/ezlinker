package com.ezlinker.app.modules.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "ez_user_role_permission_view", autoResultMap = true)
public class UserRolePermissionView extends XEntity {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;

    private Integer permissionId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> allow;

    /**
     * 请求路径
     */

    private String resource;

    /**
     * 允许的方法
     */
    @TableField(typeHandler = JacksonTypeHandler.class)

    private List<String> methods;


}
