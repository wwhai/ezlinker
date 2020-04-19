package com.ezlinker.app.modules.relation.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户-角色关联
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_relation_user_role")
public class RelationUserRole extends XEntity {

    private static final long serialVersionUID=1L;

    private Long userId;

    private Long roleId;


}
