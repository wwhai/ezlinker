package com.ezlinker.app.modules.relation.service.impl;

import com.ezlinker.app.modules.relation.model.RelationUserProject;
import com.ezlinker.app.modules.relation.mapper.RelationUserProjectMapper;
import com.ezlinker.app.modules.relation.service.IRelationUserProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和项目关联表，用来给用户授权具体项目 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-19
 */
@Service
public class RelationUserProjectServiceImpl extends ServiceImpl<RelationUserProjectMapper, RelationUserProject> implements IRelationUserProjectService {

}
