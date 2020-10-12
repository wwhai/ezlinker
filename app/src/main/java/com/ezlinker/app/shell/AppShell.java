package com.ezlinker.app.shell;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezlinker.app.modules.user.model.User;
import com.ezlinker.app.modules.user.model.UserProfile;
import com.ezlinker.app.modules.user.service.IUserProfileService;
import com.ezlinker.app.modules.user.service.IUserService;
import com.ezlinker.app.utils.HelpfulUtil;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wangwenhai
 * @date 2020/10/8
 * File description: Shell 支持
 */
@ShellComponent
public class AppShell {
    @Resource
    IUserService iUserService;
    @Resource
    IUserProfileService iUserProfileService;

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("ezlinker-shell:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }

    /**
     * 创建一个系统内部用户
     *
     * @param username
     * @return
     */

    @ShellMethod("Create An User, example: create-user ${username} ${phone}")
    @Transactional(rollbackFor = Exception.class)
    public String createUser(@ShellOption String username, @ShellOption String phone) {
        User tempUser = iUserService.getOne(new QueryWrapper<User>().eq("username", username));
        if (tempUser != null) {
            return "User already exists!";
        }
        User user = new User();
        String password = HelpfulUtil.getRandomString(8);
        user.setUsername(username)
                .setPhone(phone)
                .setPassword(SecureUtil.md5(password));
        UserProfile userProfile = new UserProfile();
        iUserProfileService.save(userProfile);
        user.setUserProfileId(userProfile.getId());
        boolean ok = iUserService.save(user);
        if (ok) {
            return "User create success, username is:" + username + " password is :" + password;
        } else {
            return "User create failed!";
        }
    }

    /**
     * 删除一个用户
     *
     * @param username
     * @return
     */
    @ShellMethod("Remove An User, example: remove-user ${username}")
    @Transactional(rollbackFor = Exception.class)
    public String removeUser(@ShellOption String username) {
        QueryWrapper<User> queryWrap1 = new QueryWrapper<User>().eq("username", username);

        User user = iUserService.getOne(queryWrap1);
        if (user != null) {
            boolean ok1 = iUserService.remove(queryWrap1);
            QueryWrapper<UserProfile> queryWrap2 = new QueryWrapper<UserProfile>().eq("id", user.getUserProfileId());
            boolean ok2 = iUserProfileService.remove(queryWrap2);
            if (ok1 && ok2) {
                return "User:" + username + " remove successful.";
            } else {
                return "User:" + username + " remove failed.";
            }
        } else {
            return "User:" + username + " not exists.";

        }

    }
}
