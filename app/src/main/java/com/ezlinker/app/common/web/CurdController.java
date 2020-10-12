package com.ezlinker.app.common.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.PermissionForbiddenException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.model.XEntity;
import com.ezlinker.app.common.query.QueryItem;
import com.ezlinker.app.modules.authorize.model.ResourceAuthorize;
import com.ezlinker.app.modules.authorize.service.IResourceAuthorizeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: ezlinker
 * @description: 基础Controller
 * @author: wangwenhai
 * @create: 2019-11-04 17:10
 **/
public abstract class CurdController<T> extends XController {


    public CurdController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 添加一个T
     *
     * @param t
     * @return
     */
    @Transactional(rollbackFor = Exception.class)

    @PostMapping
    protected R add(T t) throws XException {
        return success();
    }

    /**
     * 2020-08-31更新
     * 后续将简单业务的新建和更新作为一个接口
     *
     * @param t
     * @return
     * @throws XException
     */
    @PostMapping("/create")
    protected R create(T t) throws XException {

        return success();
    }


    /**
     * 动态参数请求
     * 一般用Map来构建
     *
     * @param queryArgs
     * @return
     * @throws XException
     */
    @PostMapping("/query")
    protected R query(List<QueryItem> queryArgs) throws XException {
        return success();
    }

    /**
     * 批量删除T
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)

    @DeleteMapping("/{ids}")
    protected R delete(Integer[] ids) throws XException {
        return success();
    }


    /**
     * 更新T
     *
     * @param t
     * @return
     */
    @PutMapping(value = "/{id}")
    @Transactional(rollbackFor = Exception.class)

    protected R update(Long id, T t) throws XException {
        return success();
    }

    /**
     * 查询单个T
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    protected R get(Long id) throws XException {
        return success();
    }


    /**
     * 分页不带条件查询
     *
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/list")
    protected R list(Integer current,
                     Integer size) {
        return success();
    }

    /**
     * @return
     */
    @GetMapping("/all")
    protected R all() throws Exception {
        return success();
    }

    /**
     * 检查Model是否为空值
     *
     * @param entity
     * @return
     * @throws BadRequestException
     */
    protected final void checkModelNull(XEntity entity) throws BadRequestException {
        if (entity == null)
            throw new BadRequestException("资源不存在", "Resource not exists");
    }

    /**
     * 带消息返回空
     *
     * @param entity
     * @param message
     * @param i18nMessage
     * @throws BadRequestException
     */
    protected final void checkModelNull(XEntity entity, String message, String i18nMessage) throws BadRequestException {

        if (entity == null)
            throw new BadRequestException("资源不存在", "Resource not exists");


    }

    /**
     * 检查资源权限
     * 主要用在检查某个资源是否可以被用户操作
     *
     * @param masterId 主表ID
     * @param slaverId 从表ID
     * @return
     */
    protected boolean checkResourceAuthorize(IResourceAuthorizeService iResourceAuthorizeService, Long masterId, Long slaverId) throws XException {
        QueryWrapper<ResourceAuthorize> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceAuthorize.MASTER_ID, masterId).eq(ResourceAuthorize.SLAVER_ID, slaverId).orderBy(false, false, "id");
        // 查找权限表
        ResourceAuthorize resourceAuthorize = iResourceAuthorizeService.getOne(queryWrapper);
        if (resourceAuthorize == null) {
            throw new PermissionForbiddenException("权限不足,禁止访问", "No access permission!");
        } else {
            String method = this.httpServletRequest.getMethod().toLowerCase();
            Integer authorizeValue = resourceAuthorize.getAuthorizeValue();
            switch (method) {
                case "post":
                    if (authorizeValue == 8) {
                        return true;
                    } else {
                        throw new PermissionForbiddenException("权限不足,禁止访问", "No access permission!");
                    }
                case "get":
                    if (authorizeValue == 2) {
                        return true;
                    } else {
                        throw new PermissionForbiddenException("权限不足,禁止访问", "No access permission!");
                    }
                case "delete":
                    if (authorizeValue == 1) {
                        return true;
                    } else {
                        throw new PermissionForbiddenException("权限不足,禁止访问", "No access permission!");
                    }
                case "put":
                    if (authorizeValue == 4) {
                        return true;
                    } else {
                        throw new PermissionForbiddenException("权限不足,禁止访问", "No access permission!");
                    }
                default:
                    return false;
            }
        }

    }

    /**
     * 产生分页
     *
     * @param current
     * @param size
     * @return
     */
    protected final IPage<T> p(Integer current, Integer size) {
        return new Page<>(current, size);
    }

    /**
     * 头疼:Mongo template 下标从0开始
     * 专门提供一个生成Mongo分页的的Page方法
     *
     * @param current
     * @param size
     * @return
     */
    protected PageRequest getXPageRequest(int current, int size) {
        return PageRequest.of(current >= 1 ? current - 1 : current, size, Sort.by(Sort.Direction.DESC, "_id"));
    }

    /**
     * Mysql 分页对象生成器
     *
     * @param current
     * @param size
     * @return
     */
    protected PageRequest getMYPageRequest(int current, int size) {
        return PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "id"));
    }
}
