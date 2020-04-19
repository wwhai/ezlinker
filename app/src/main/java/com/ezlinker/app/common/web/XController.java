package com.ezlinker.app.common.web;

import com.ezlinker.app.common.exception.TokenException;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.exchange.RCode;
import com.ezlinker.app.modules.user.model.UserDetail;
import com.ezlinker.app.utils.UserTokenUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ezlinker
 * @description: 基础Controller
 * @author: wangwenhai
 * @create: 2019-11-04 17:10
 **/
public abstract class XController {


    /**
     * httpServletRequest
     */
    protected HttpServletRequest httpServletRequest;

    public XController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 获取当前用户的信息
     *
     * @return
     */
    protected UserDetail getUserDetail() throws XException {
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            return UserTokenUtil.parse(token);
        } else {
            throw new TokenException("Missing token,please try again", "Token缺失,请重新获取");
        }
    }


    /**
     * 失败返回
     *
     * @return
     */
    protected R fail() {
        Integer code = RCode.FAIL.getCode();
        String message = RCode.FAIL.getMessage();
        String i8nMessage = RCode.FAIL.getI8nMessage();
        return new R(code, message, i8nMessage, null);
    }

    /**
     * 成功返回
     *
     * @return
     */
    protected R success() {
        Integer code = RCode.SUCCESS.getCode();
        String message = RCode.SUCCESS.getMessage();
        String i8nMessage = RCode.SUCCESS.getI8nMessage();
        return new R(code, message, i8nMessage, null);
    }

    protected R message(String msg) {
        Integer code = RCode.SUCCESS.getCode();
        String message = RCode.SUCCESS.getMessage();
        String i8nMessage = RCode.SUCCESS.getI8nMessage();
        return new R(code, message, i8nMessage, msg);
    }

    /**
     * 自定义成功返回状态码
     *
     * @param data
     * @return
     */
    protected R data(Object data) {
        Integer code = RCode.SUCCESS.getCode();
        String message = RCode.SUCCESS.getMessage();
        String i8nMessage = RCode.SUCCESS.getI8nMessage();
        return new R(code, message, i8nMessage, data);
    }


}
