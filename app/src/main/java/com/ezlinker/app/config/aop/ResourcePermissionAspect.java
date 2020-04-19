package com.ezlinker.app.config.aop;

import com.ezlinker.app.common.exception.XException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResourcePermissionAspect {

    /**
     * 检查资源的更新权限
     * request.getRequestURL() 返回全路径
     * request.getRequestURI() 返回除去host（域名或者ip）部分的路径
     * request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
     * request.getServletPath() 返回除去host和工程名部分的路径
     */
    @Before("execution(* com.ezlinker.app.modules.*.controller.*.update(..))")
    public void checkUpdatePermission(JoinPoint point) throws XException {
        // TODO 权限拦截管理,后期实现
//        //获取到请求的属性
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            MethodSignature signature = (MethodSignature) point.getSignature();
//
//            HttpServletRequest request = attributes.getRequest();
//            XLazyMan xLazyMan = new XLazyMan(request);
//            Object[] args = point.getArgs();
//            Long id = (Long) args[0];
//            Long userId = xLazyMan.getUserDetail().getId();
//            String resource = request.getServletPath();
//            String method = signature.getMethod().getName();
//            System.out.println("用户 " + userId + " 请求的资源: " + resource + " 下的数据:" + id + " 的操作类型 " + method);
//        } else {
//            throw new InternalServerException("服务器内部错误", "Internal server error");
//        }
//

    }

    /**
     * 检查资源的删除权限
     */
    @Before("execution(* com.ezlinker.app.modules.*.controller.*.delete(..))")
    public void checkDeletePermission(JoinPoint point) {

    }
}
