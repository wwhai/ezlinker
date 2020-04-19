package com.ezlinker.app.modules.relation.service.impl;

import com.ezlinker.app.modules.relation.model.RelationUserRole;
import com.ezlinker.app.modules.relation.mapper.RelationUserRoleMapper;
import com.ezlinker.app.modules.relation.service.IRelationUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@Service
public class RelationUserRoleServiceImpl extends ServiceImpl<RelationUserRoleMapper, RelationUserRole> implements IRelationUserRoleService {

}
