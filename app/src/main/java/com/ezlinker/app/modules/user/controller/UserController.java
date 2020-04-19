package com.ezlinker.app.modules.user.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.utils.RegxUtil;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.role.service.IRoleService;
import com.ezlinker.app.modules.user.form.AddUserForm;
import com.ezlinker.app.modules.user.form.ResetPasswordForm;
import com.ezlinker.app.modules.user.form.UserUpdateForm;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserProfile;
import com.ezlinker.app.modules.user.service.IUserProfileService;
import com.ezlinker.app.modules.user.service.IUserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@RestController
@RequestMapping("/users")
public class UserController extends CurdController<User> {

    @Resource
    IUserService iUserService;

    @Resource
    IUserProfileService iUserProfileService;

    @Resource
    IRoleService iRoleService;

    public UserController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    /**
     * 获取用户的操作菜单
     *
     * @return
     * @throws XException
     */
    @GetMapping("/roles")
    public R getRoles() throws XException {
        return data(iUserService.getAllRoles(getUserDetail().getId()));
    }

    /**
     * 获取所有权限
     *
     * @return
     * @throws XException
     */
    @GetMapping("/permissions")
    public R getPermissions() throws XException {
        return data(iUserService.getAllPermissions(getUserDetail().getId()));
    }

    /**
     * 获取用户详情
     *
     * @return
     * @throws XException
     */
    @GetMapping("/userInfo")
    public R getUserInfo() throws XException {
        return data(iUserService.getUserInfo(getUserDetail().getId()));
    }


    /**
     * 重置密码
     *
     * @return
     */
    @PutMapping("/resetPassword")
    public R resetPassword(@RequestBody ResetPasswordForm form) throws XException {
        if (!form.getNewPassword().equals(form.getPasswordRetry())) {
            throw new BizException("Invalid password!", "两次密码不一致");
        }
        User user = iUserService.getById(getUserDetail().getId());
        if (!user.getPassword().toUpperCase().equals(SecureUtil.md5(form.getNewPassword()))) {
            throw new BizException("Invalid password!", "旧密码错误");
        }
        user.setPassword(SecureUtil.md5(form.getNewPassword()));
        boolean ok = iUserService.updateById(user);
        return ok ? success() : fail();

    }

    /**
     * 更新用户信息
     *
     * @param form
     * @return
     * @throws XException
     */
    @PutMapping("/info")
    public R updateInfo(@RequestBody UserUpdateForm form) throws XException {
        User user = iUserService.getById(getUserDetail().getId());
        if (!StringUtils.isEmpty(form.getPhone())) {
            if (!RegxUtil.isPhone(form.getPhone())) {
                throw new BizException("Invalid phone format!", "手机号码错误");

            } else {
                user.setPhone(form.getPhone());

            }

        }
        if (!StringUtils.isEmpty(form.getRealName())) {
            user.setRealName(form.getRealName());

        }
        if (!StringUtils.isEmpty(form.getNickName())) {
            user.setNickName(form.getNickName());

        }
        if (!StringUtils.isEmpty(form.getEmail())) {
            if (!RegxUtil.isEmail(form.getEmail())) {
                throw new BizException("Invalid email format!", "邮箱格式错误");
            } else {
                user.setEmail(form.getEmail());

            }
        }
        boolean ok = iUserService.updateById(user);
        return ok ? success() : fail();

    }

    /**
     * 创建用户
     *
     * @param addUserForm
     * @return
     */
    @PostMapping("/addUser")
    public R addUser(@RequestBody @Valid AddUserForm addUserForm) throws XException {
        User tmp = iUserService.getOne(new QueryWrapper<User>()
                .eq(User.Fields.username, addUserForm.getUsername()).or().eq(User.Fields.email, addUserForm.getEmail()));
        if (tmp != null) {
            throw new BizException("User already exists", "用户已存在");
        }

        User user = new User();
        user.setUsername(addUserForm.getUsername())
                .setPassword(SecureUtil.md5("12345678"))
                .setPhone(addUserForm.getPhone())
                .setEmail(addUserForm.getEmail());
        UserProfile userProfile = new UserProfile();
        iUserProfileService.save(userProfile);
        user.setUserProfileId(userProfile.getId());
        boolean ok = iUserService.save(user);
        return ok ? success() : fail();

    }
}

