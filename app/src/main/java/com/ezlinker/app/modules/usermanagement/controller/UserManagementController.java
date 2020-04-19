package com.ezlinker.app.modules.usermanagement.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ezlinker.app.common.exception.BadRequestException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.utils.AliyunEmailUtil;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserProfile;
import com.ezlinker.app.modules.user.service.IUserProfileService;
import com.ezlinker.app.modules.user.service.IUserService;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>Title: UserListController</p>
 * <p>Description: 后台管理员-用户管理-</p>
 *
 * @author zhaolei
 * @version: V1.0.0
 * @date: 2019-11-26 15:08
 */
@RestController
@RequestMapping("/management/users")
public class UserManagementController extends CurdController<User> {
    /**
     * 账户状态
     */
    public static final String STATUS = "status";
    /**
     * 账户类型
     */
    public static final String TYPE = "userType";
    private final IUserService iUserService;
    private final IUserProfileService iUserProfileService;
    private final AliyunEmailUtil aliyunEmailUtil;


    public UserManagementController(HttpServletRequest httpServletRequest, IUserService iUserService, IUserProfileService iUserProfileService, AliyunEmailUtil aliyunEmailUtil) {
        super(httpServletRequest);
        this.iUserService = iUserService;
        this.iUserProfileService = iUserProfileService;
        this.aliyunEmailUtil = aliyunEmailUtil;
    }


    /**
     * 查询用户列表
     *
     * @param current 当前页
     * @param size    条数
     * @return
     */
    @GetMapping
    public R queryForPage(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        /**
         * TODO 后期增加过滤条件，如：用户类型、账号状态、用户名等
         */

        IPage<User> page = iUserService.page(new Page<>(current, size));
        return data(page);
    }

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return
     */
    @Override
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        return data(iUserService.getUserInfo(id));
    }


    /**
     * 用户账户操作
     *
     * @param userId 用户ID
     * @param action 操作类型
     * @param value  操作参数
     * @return
     * @throws XException
     */
    @PutMapping("/{userId}/{action}")
    public R updateUserAccount(@PathVariable("userId") Long userId,
                               @NotBlank(message = "操作方法不能为空") @PathVariable String action,
                               @NotBlank(message = "操作值不能为空") @RequestParam String value) throws XException {
        if (iUserService.getUserInfo(userId) == null) {
            throw new BadRequestException("User Not Exists", "用户不存在");
        }

        User user = new User();
        user.setId(userId);

        switch (action) {
            case STATUS:
                user.setStatus(Integer.parseInt(value));
                break;
            case TYPE:
                user.setUserType(Integer.parseInt(value));
                break;
            default:
                throw new BadRequestException("Action Not Support", "不支持的操作");
        }

        boolean result = iUserService.updateById(user);
        return result ? success() : fail();
    }

    /**
     * 添加用户账户
     *
     * @param userForm 用户信息
     * @return
     * @throws XException
     */


    @Transactional(rollbackFor = Exception.class)
    @Override
    protected R add(@RequestBody User userForm) throws XException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .eq(User.Fields.username, userForm.getUsername())
                .or()
                .eq(User.Fields.email, userForm.getEmail());
        if (iUserService.getOne(queryWrapper) != null) {
            throw new BadRequestException("User Already Exists", "用户已存在");
        }

        User user = new User()
                .setAvatar("1")
                .setUsername(userForm.getUsername())
                .setPassword(SecureUtil.md5("12345678"))
                .setPhone(userForm.getPhone())
                .setEmail(userForm.getEmail());
        UserProfile userProfile = new UserProfile();
        iUserProfileService.save(userProfile);
        user.setUserProfileId(userProfile.getId());
        boolean result = iUserService.save(user);
        return result ? data(user) : fail();
    }


    /**
     * 发送邮件
     *
     * @param sendEmailEntity 邮件内容
     * @return
     * @throws XException
     */
    @PostMapping("/sendEmail")
    public R sendEmail(@Valid @RequestBody SendEmailEntity sendEmailEntity) throws XException {
        aliyunEmailUtil.sendHtmlMail(sendEmailEntity.getAddress(), sendEmailEntity.getSubject(), sendEmailEntity.getContent());
        return success();
    }


    @Data
    private static class SendEmailEntity {
        /**
         * 邮箱地址
         */
        @NotNull(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String address;

        /**
         * 主题
         */
        @NotNull(message = "邮件主题不能为空")
        private String subject;

        /**
         * 邮件内容
         */
        @NotNull(message = "邮件内容不能为空")
        private String content;
    }


}
