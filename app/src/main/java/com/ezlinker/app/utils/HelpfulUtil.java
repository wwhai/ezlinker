package com.ezlinker.app.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangwenhai
 * @date 2020/8/16
 * File description:
 */
public class HelpfulUtil {

    /**
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

    /**
     * @param ip
     * @return
     */
    public static String getLocationWithIp(String ip) {
        try {
            String result = HttpUtil.get("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip);

            JSONObject data = JSONObject.parseObject(result);
            return data.getString("pro") + data.getString("city") + data.getString("addr");
        } catch (Exception e) {
            return "IP详细信息获取失败";
        }
    }

}
