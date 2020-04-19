package com.ezlinker.app.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.internalmessage.service.InternalMessageService;
import com.ezlinker.app.modules.permission.model.Permission;
import com.ezlinker.app.modules.role.model.Role;
import com.ezlinker.app.modules.user.mapper.UserMapper;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserInfo;
import com.ezlinker.app.modules.user.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@Validated
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    InternalMessageService internalMessageService;


    /**
     * 获取用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getAllRoles(Long userId) {
        return userMapper.getAllRoles(userId);
    }

    /**
     * 获取权限列表
     * @param userId
     * @return
     */
    @Override
    public List<Permission> getAllPermissions(@NotNull(message = "用户ID不能为空") Long userId) {
        return userMapper.getPermissions(userId);
    }


    /**
     * 获取用户详情资料
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserInfo(@NotNull(message = "用户ID不能为空") Long userId) {
        UserInfo userInfoView = userMapper.getUserInfo(userId);
        long count = internalMessageService.count(userId);
        userInfoView.setMsgCount(count);
        return userInfoView;
    }
}
