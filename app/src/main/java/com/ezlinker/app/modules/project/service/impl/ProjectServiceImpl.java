package com.ezlinker.app.modules.project.service.impl;

import com.ezlinker.app.modules.project.model.Project;
import com.ezlinker.app.modules.project.mapper.ProjectMapper;
import com.ezlinker.app.modules.project.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 项目 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Resource
    ProjectMapper projectMapper;

    @Override
    public List<Project> listAllAuthorizeProjectByUserId(Long userId) {
        return projectMapper.listAllAuthorizeProjectByUserId(userId);
    }
}
