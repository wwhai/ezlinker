package com.ezlinker.app.modules.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.project.model.Project;
import com.ezlinker.app.modules.project.service.IProjectService;
import com.ezlinker.app.modules.relation.model.RelationUserProject;
import com.ezlinker.app.modules.relation.service.IRelationUserProjectService;
import com.ezlinker.app.modules.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 项目
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends CurdController<Project> {
    @Resource
    IProjectService iProjectService;

    @Resource
    IRelationUserProjectService iRelationUserProjectService;

    @Resource
    IUserService iUserService;

    public ProjectController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 创建项目
     *
     * @param project 项目:必传
     * @return
     */
    @Override
    protected R add(@RequestBody @Valid Project project) throws XException {
        boolean ok = iProjectService.save(project);
        return ok ? success() : fail();
    }

    /**
     * 获取图标,使用了一个免费图床:https://imgchr.com
     *
     * @return
     */
    @GetMapping("/icons")
    public R icons() {
        List<String> urls = new ArrayList<>();
        urls.add("https://s2.ax1x.com/2020/03/03/3fzDo9.th.png");
        urls.add("https://s2.ax1x.com/2020/03/03/3fz6Rx.th.png");
        urls.add("https://s2.ax1x.com/2020/03/03/3fztzV.th.png");
        return data(urls);
    }


    /**
     * 删除项目
     *
     * @param ids 批量删除的ID数组:必传
     * @return
     */

    @Override
    protected R delete(@PathVariable Integer[] ids) {
        boolean ok = iProjectService.removeByIds(Arrays.asList(ids));
        return ok ? success() : fail();
    }

    /**
     * 更新项目信息
     *
     * @param project 项目:必传
     * @return
     * @throws XException
     */
    @Override
    protected R update(@PathVariable Long id, @RequestBody Project project) throws XException {

        if (iProjectService.getById(id) == null) {
            throw new XException("Project not exists!", "项目不存在");
        }
        project.setId(id);
        boolean ok = iProjectService.updateById(project);
        return ok ? data(project) : fail();
    }


    /**
     * 查看项目详情
     *
     * @param id 项目ID:必传
     * @return
     */

    @Override
    protected R get(@PathVariable Long id) throws XException {
        Project project = iProjectService.getById(id);
        if (project == null) {
            throw new XException("Project not exists!", "项目不存在");
        }
        return data(project);
    }


    /**
     * 条件检索
     *
     * @param self     如果传self，则查询和自己有关的，不传则查询所有：选传
     * @param name     项目名称：选传
     * @param location 项目位置：选传
     * @param current  页码：必传
     * @param size     页长：必传
     * @return
     * @throws XException
     */
    @GetMapping
    public R queryForPage(
            @RequestParam(required = false) boolean self,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam Integer current,
            @RequestParam Integer size) throws XException {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        if (self) {
            queryWrapper.eq("user_id", getUserDetail().getId());

        }
        queryWrapper.like(name != null, "name", name);
        queryWrapper.like(location != null, "location", location);
        queryWrapper.orderByDesc("create_time");
        IPage<Project> projectPage = iProjectService.page(new Page<>(current, size), queryWrapper);

        return data(projectPage);
    }


    /**
     * ------------------------------------------------------------------------------------------------
     * |项目授权操作 2020-2-19
     * ------------------------------------------------------------------------------------------------
     */


    /**
     * 授权项目
     *
     * @param userId
     * @param projectId
     * @return
     * @throws XException
     */
    @PostMapping("/authorizeProject")
    public R authorizeProject(@RequestParam Long userId, @RequestParam Long projectId) throws XException {

        checkModelNull(iUserService.getById(userId), "User not exist!", "用户不存在");
        checkModelNull(iProjectService.getById(projectId), "Project not exist!", "项目不存在");

        RelationUserProject temp = iRelationUserProjectService.getOne(new QueryWrapper<RelationUserProject>().eq(RelationUserProject.USER_ID, userId).eq(RelationUserProject.PROJECT_ID, projectId));
        if (temp != null) {
            throw new BizException("Relation has exists!", "该项目已经授权于该用户操作权限，请勿重复授权");

        }
        // 构建关系
        RelationUserProject relationUserProject = new RelationUserProject();
        relationUserProject.setUserId(userId).setProjectId(projectId);
        iRelationUserProjectService.save(relationUserProject);
        return success();
    }

    /**
     * 取消授权
     *
     * @param userId
     * @param projectId
     * @return
     * @throws XException
     */
    @PutMapping("/unAuthorizeProject")
    public R unAuthorizeProject(@RequestParam Long userId, @RequestParam Long projectId) throws XException {
        // 判断用户是否存在
        checkModelNull(iUserService.getById(userId), "User not exist!", "用户不存在");
        checkModelNull(iProjectService.getById(projectId), "Project not exist!", "项目不存在");

        RelationUserProject relationUserProject = iRelationUserProjectService.getOne(new QueryWrapper<RelationUserProject>().eq(RelationUserProject.USER_ID, userId).eq(RelationUserProject.PROJECT_ID, projectId));
        if (relationUserProject == null) {
            throw new BizException("Relation not exist!", "该用户和项目没有绑定关系");

        }
        // 删除关系
        iRelationUserProjectService.removeById(relationUserProject);
        return success();
    }
}

