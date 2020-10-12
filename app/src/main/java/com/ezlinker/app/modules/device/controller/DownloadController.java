package com.ezlinker.app.modules.device.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangwenhai
 * @date 2020/10/9
 * File description: 文件下载入口
 */
@Controller
@RequestMapping("/devices")
public class DownloadController {

    @Resource
    IDeviceService iDeviceService;

    /**
     * 下载认证文件
     *
     * @param response
     * @param clientId
     * @return
     * @throws IOException¬
     */
    @RequestMapping("/downloadAuthFile")
    public void downloadAuthFile(HttpServletResponse response,
                                 @RequestParam String clientId) throws IOException {
        String tpl = "配置项,值\n" +
                "Username,#1\n" +
                "password,#2\n" +
                "clientid,#3\n" +
                "token,#4\n";
        Device device = iDeviceService.getOne(new QueryWrapper<Device>().eq("client_id", clientId));
        if (device != null) {
            tpl = tpl.replace("#1", device.getUsername())
                    .replace("#2", device.getPassword())
                    .replace("#3", device.getClientId())
                    .replace("#4", device.getToken());
            response.reset();
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength(tpl.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + clientId + ".csv");
            response.getOutputStream().write(tpl.getBytes());
            response.getOutputStream().flush();
        }

    }
}
