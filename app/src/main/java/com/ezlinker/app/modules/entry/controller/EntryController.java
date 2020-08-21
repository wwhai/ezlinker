package com.ezlinker.app.modules.entry.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezlinker.app.common.exception.AuthorizedFailedException;
import com.ezlinker.app.common.exception.PasswordInvalidException;
import com.ezlinker.app.common.exception.UserNotFoundException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.exchange.RCode;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.modules.entry.form.UserLoginForm;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserDetail;
import com.ezlinker.app.modules.user.service.IUserService;
import com.ezlinker.app.modules.userlog.model.UserLoginLog;
import com.ezlinker.app.modules.userlog.service.IUserLoginLogService;
import com.ezlinker.app.utils.HelpfulUtil;
import com.ezlinker.app.utils.UserTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: ezlinker
 * @description: 入口
 * @author: wangwenhai
 * @create: 2019-11-11 17:44
 **/
@RestController
@RequestMapping("/entry")
@Slf4j
public class EntryController {


    @Resource
    RedisUtil redisUti;
    @Resource
    IUserService iUserService;

    @Resource
    IUserLoginLogService<UserLoginLog> iUserLoginLogService;

    /**
     * 用户登录
     *
     * @param loginForm 登录表单
     * @return
     * @throws AuthorizedFailedException
     */
    @Transactional(rollbackFor = Exception.class, noRollbackFor = AuthorizedFailedException.class)
    @PostMapping("/login")
    public R login(@RequestBody UserLoginForm loginForm, HttpServletRequest request) throws XException {
        User user = iUserService.getOne(new QueryWrapper<User>().eq("username", loginForm.getUsername()));
        String ip = HelpfulUtil.getIpAddress(request);

        if (user == null) {

            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setCreateTime(new Date());
            userLoginLog.setUsername(null).setIp(ip).setStatus("WARNING").setUserId(0L).setRemark("未知用户尝试登陆失败").setLocation(HelpfulUtil.getLocationWithIp(ip));
            iUserLoginLogService.save(userLoginLog);
            throw new UserNotFoundException("Login failure,user not exists!", "登陆失败,用户不存在");
        }
        if (!user.getPassword().toUpperCase().equals(SecureUtil.md5(loginForm.getPassword()).toUpperCase())) {
            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setCreateTime(new Date());
            userLoginLog.setUsername(user.getUsername()).setIp(ip).setStatus("WARNING").setUserId(user.getId()).setRemark("尝试登陆失败").setLocation(HelpfulUtil.getLocationWithIp(ip));
            iUserLoginLogService.save(userLoginLog);
            throw new PasswordInvalidException("Login failure,password invalid!", "登陆失败,密码错误");
        }
        /**
         * 构建UserDetail
         */
        UserDetail userDetail = new UserDetail();
        userDetail.setId(user.getId());
        userDetail.setUsername(user.getUsername());
        userDetail.setUserType(user.getUserType());
        userDetail.setExpiredTime(UserTokenUtil.expiredTime);

        String token = UserTokenUtil.token(userDetail, UserTokenUtil.expiredTime);
        redisUti.set(user.getId().toString(), token, UserTokenUtil.expiredTime);
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setCreateTime(new Date());
        userLoginLog.setUsername(user.getUsername()).setIp(ip).setStatus("INFO").setUserId(user.getId()).setRemark("登陆成功").setLocation(HelpfulUtil.getLocationWithIp(ip));
        iUserLoginLogService.save(userLoginLog);
        // 更新登陆信息
        user.setLastLoginIp(ip).setLastLoginTime(new Date());
        iUserService.updateById(user);
        return new R(RCode.SUCCESS.getCode(), RCode.SUCCESS.getMessage(), RCode.SUCCESS.getI8nMessage(), token);

    }

}
