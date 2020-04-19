package com.ezlinker.app.modules.analyse.controller;

import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.XController;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description EZLinker监控
 * @create 2019-12-17 21:21
 **/
@RestController
@RequestMapping("/monitor/ezlinker")
public class EZLinkerMonitorController extends XController {

    OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public EZLinkerMonitorController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);

    }

    @GetMapping("/info")
    public R info() {

        Map<String, Object> info = new LinkedHashMap<>(5);

        Map<String, Object> memory = new LinkedHashMap<>(2);
        //转化为MB
        int MB = 1024 * 1024;
        double totalMem = (double) mem.getTotalPhysicalMemorySize() / MB;
        memory.put("total", totalMem);
        double freeMem = (double) mem.getFreePhysicalMemorySize() / MB;
        memory.put("free", freeMem);
        info.put("memory", memory);

        double cpuLoad = mem.getSystemCpuLoad();
        info.put("cpuLoad", cpuLoad);

        Map<String, Object> spaces = new HashMap<>(5);
        File[] disks = File.listRoots();
        for (File file : disks) {
            long freeSpace = file.getFreeSpace() / MB;
            long totalSpace = file.getTotalSpace() / MB;
            if (totalSpace > 0) {
                Map<String, Object> space = new HashMap<>(2);
                space.put("free", freeSpace);
                space.put("total", totalSpace);
                spaces.put(file.getPath(), space);
            }
        }
        info.put("spaces", spaces);


        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            info.put("hostAddr", ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return fail();
        }

        return data(info);
    }

}
