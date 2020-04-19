package com.ezlinker.app.modules.entry.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
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
import com.ezlinker.app.utils.UserTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String LOCAL_IPV4 = "127.0.0.1";
    private static final String LOCAL_IPV6 = "0:0:0:0:0:0:0:1";

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
        String ip = getIp(request);

        if (user == null) {

            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setUsername(null).setIp(ip).setStatus("WARNING").setUserId(0L).setRemark("未知用户尝试登陆失败").setLocation(getLocationWithIp(ip));
            iUserLoginLogService.save(userLoginLog);
            throw new UserNotFoundException("Login failure,user not exists!", "登陆失败,用户不存在");
        }

        if (!user.getPassword().toUpperCase().equals(SecureUtil.md5(loginForm.getPassword()).toUpperCase())) {
            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setUsername(user.getUsername()).setIp(ip).setStatus("WARNING").setUserId(user.getId()).setRemark("尝试登陆失败").setLocation(getLocationWithIp(ip));
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
        userLoginLog.setUsername(user.getUsername()).setIp(ip).setStatus("INFO").setUserId(user.getId()).setRemark("登陆成功").setLocation(getLocationWithIp(ip));
        iUserLoginLogService.save(userLoginLog);
        // 更新登陆信息
        user.setLastLoginIp(ip).setLastLoginTime(new Date());
        iUserService.updateById(user);
        return new R(RCode.SUCCESS.getCode(), RCode.SUCCESS.getMessage(), RCode.SUCCESS.getI8nMessage(), token);

    }

    /**
     * {
     * "ip": "156.77.80.16",
     * "pro": "",
     * "proCode": "999999",
     * "city": "",
     * "cityCode": "0",
     * "region": "",
     * "regionCode": "0",
     * "addr": "* 美国",
     * "regionNames": "",
     * "err": "noprovince"
     * }
     *
     * @param ip
     * @return
     */

    private String getLocationWithIp(String ip) {
        try {
            String result = HttpUtil.get("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip);

            JSONObject data = JSONObject.parseObject(result);
            return data.getString("pro") + data.getString("city") + data.getString("addr");
        } catch (Exception e) {
            log.error("IP获取失败，请检查IP查询接口是否正常.");
            return "IP详细信息获取失败";
        }
    }

    private String getIp(HttpServletRequest request) {
        String ip;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteHost();
            }
            if (LOCAL_IPV4.equals(ip) || LOCAL_IPV6.equals(ip)) {

                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    ip = LOCAL_IPV4;
                }
            }
        } catch (Exception e) {
            ip = "UN_KNOW";
        }
        return ip;

    }

}
