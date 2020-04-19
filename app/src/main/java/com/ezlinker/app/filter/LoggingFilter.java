//package com.ezlinker.app.filter;
//
///**
// * @program: ezlinker
// * @description: 日志打印
// * @author: wangwenhai
// * @create: 2019-11-11 16:44
// **/
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.ezlinker.app.config.RepeatedlyReadRequestWrapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.*;
//
////@Component
//public class LoggingFilter implements Filter {
//    Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
//
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) throws IOException, ServletException {
//        javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest) request;
//        Map<String, Object> map = new HashMap<>();
//
//        HttpServletRequest requestWrapper = new RepeatedlyReadRequestWrapper(req);
//
//        try {
//            // Get request URL.
//            map.put("URL", req.getRequestURL());
//            map.put("Method", req.getMethod());
//            map.put("Protocol", req.getProtocol());
//            // 获取header信息
//
//            List<Map<String, String>> headerList = new ArrayList<>();
//            Map<String, String> headerMaps = new HashMap<>();
//            for (Enumeration<String> enu = req.getHeaderNames(); enu.hasMoreElements(); ) {
//                String name = enu.nextElement();
//                headerMaps.put(name, req.getHeader(name));
//            }
//            headerList.add(headerMaps);
//            map.put("headers", headerList);
//            //获取parameters信息
//
//            List<Map<String, String>> parameterList = new ArrayList<>();
//            Map<String, String> parameterMaps = new HashMap<>();
//            for (Enumeration<String> names = req.getParameterNames(); names.hasMoreElements(); ) {
//                String name = names.nextElement();
//                parameterMaps.put(name, req.getParameter(name));
//            }
//            parameterList.add(parameterMaps);
//            map.put("parameters", parameterList);
//            String line = "";
//            // 获取请求体信息
//            if (req.getMethod().equalsIgnoreCase("POST")) {
//                int len = req.getContentLength();
//                char[] buf = new char[len];
//                int bufcount = requestWrapper.getReader().read(buf);
//                if (len > 0 && bufcount <= len) {
//                    line = String.copyValueOf(buf).substring(0, bufcount);
//                }
//            } else if (req.getMethod().equalsIgnoreCase("GET")) {
//                int idx = req.getRequestURL().indexOf("?");
//                if (idx != -1) {
//                    line = req.getRequestURL().substring(idx + 1);
//                } else {
//                    line = null;
//                }
//            }
//            if (line != null) {
//                map.put("Body:", JSONObject.parseObject(line));
//            }
//            logger.info("Debug 来自 HTTP请求:\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
//                    + JSONObject.toJSONString(map, SerializerFeature.PrettyFormat));
//            chain.doFilter(requestWrapper, response);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//}
