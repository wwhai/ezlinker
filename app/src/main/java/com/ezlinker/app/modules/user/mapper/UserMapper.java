package com.ezlinker.app.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ezlinker.app.modules.permission.model.Permission;
import com.ezlinker.app.modules.role.model.Role;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserInfo;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询角色列表
     *
     * @param userId
     * @return
     */
    List<Role> getAllRoles(Long userId);

    /**
     * 查询权限列表
     *
     * @param roleId
     * @return
     */
    List<Permission> getPermissions(Long roleId);

    /**
     * 查询用户详情
     * @param userId
     * @return
     */
    UserInfo getUserInfo(Long userId);
}
