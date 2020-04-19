package com.ezlinker.app.config.exception;

import com.ezlinker.app.common.exchange.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 4xx异常处理
 * @create 2019-11-20 23:32
 **/
@ControllerAdvice
@Slf4j
public class Exception4xxHandlerConfig {

    /**
     * 404
     *
     * @param request
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R notFountHandler(HttpServletRequest request) {
        return new R(404, "Resource not found", "资源不存在", null);
    }

    /**
     * 请求方式不受支持
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R methodNotAllowed(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return new R(405, "Method:" + e.getMethod() + " Not Allowed", "HTTP请求方法:" + e.getMethod() + " 不支持", null);
    }

    /**
     * 参数缺失
     *
     * @param e
     * @return
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public R handMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        return new R(400, e.getMessage(), e.getParameterType() + "类型的参数:'" + e.getParameterName() + "'缺失", null);
    }


    //MethodArgumentTypeMismatchException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public R handMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        return new R(400, e.getMessage(), "路径参数错误", null);
    }

    /**
     * 参数验证失败
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R bindExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for (ObjectError error : errors) {
            buffer.append(error.getDefaultMessage()).append(";");
        }
        buffer.append("]");
        return new R(400, "Invalid param!", buffer.toString(), null);

    }
}
