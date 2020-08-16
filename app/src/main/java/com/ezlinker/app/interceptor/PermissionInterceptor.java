package com.ezlinker.app.interceptor;

import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.utils.HelpfulUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: ezlinker
 * @description: 认证拦截器
 * @author: wangwenhai
 * @create: 2019-11-07 10:02
 **/
public class PermissionInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(request.getServletPath() + " Method:" + request.getMethod() + " ->:" + HelpfulUtil.getIpAddress(request));

        if (!hasToken(request)) {
            throw new XException(401, "Require token", "Token缺失");
        }
        return true;

    }

    /**
     * 是否包含Token
     *
     * @param request
     * @return
     */
    private boolean hasToken(HttpServletRequest request) {

        String token = request.getHeader("token");
        return token != null && token.length() >= 20;

    }
}
