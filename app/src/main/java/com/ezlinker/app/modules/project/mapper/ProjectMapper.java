package com.ezlinker.app.modules.project.mapper;

import com.ezlinker.app.modules.project.model.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 项目 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
public interface ProjectMapper extends BaseMapper<Project> {

     List<Project> listAllAuthorizeProjectByUserId(Long userId);
}
