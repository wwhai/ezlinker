package com.ezlinker.app.config.exception;

import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 5xx处理
 * @create 2019-11-20 23:31
 **/
@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class Exception5xxHandlerConfig {

    /**
     * XException
     */
    @ExceptionHandler(XException.class)
    public void handXException(HttpServletResponse response, XException e) throws IOException {
        e.printStackTrace();
        log.error(e.getClass() + ":" + e.getMessage() + "[" + e.getI18nMessage() + "]");
        response.setStatus(e.getCode());
        response.setHeader("content-type", "application/json;charset=UTF-8");
        Integer code = e.getCode();
        String message = e.getMessage();
        String i8nMessage = e.getI18nMessage();
        response.getWriter().write((new R(code, message, i8nMessage, null)).toString());
        response.getWriter().flush();
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handException(Exception e) {
        e.printStackTrace();
        log.error(e.getClass() + ":" + e.getMessage());
        return new R(500, "Server internal error", "服务器内部错误", null);
    }


}
