package com.ezlinker.app.modules.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezlinker.app.modules.project.model.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
public interface IProjectService extends IService<Project> {
    /**
     * 获取所有已经授权的项目
     *
     * @param userId
     * @return
     */
    List<Project> listAllAuthorizeProjectByUserId(@Param("userId") Long userId);

}
