package com.ezlinker.app.common.utils;

import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: ezlinker
 * @description: 系统参数获取工具
 * @author: wangwenhai
 * @create: 2019-11-13 09:56
 **/
public class OSMonitor {
    private static int byteToMb = 1024 * 1024;

    /**
     * 操作系统参数
     *
     * @return
     */
    public static Map<String, Object> getOSInfo() {
        Properties properties = System.getProperties();
        Map<String, Object> data = new HashMap<>(16);
        data.put("os", properties.getProperty("os.name"));
        data.put("osVersion", properties.getProperty("os.version"));
        data.put("cpuArc", properties.getProperty("sun.cpu.isalist"));
        OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long physicalFree = mxBean.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = mxBean.getTotalPhysicalMemorySize() / byteToMb;
        long physicalUse = physicalTotal - physicalFree;

        data.put("physicalFree", physicalFree);
        data.put("physicalTotal", physicalTotal);
        data.put("physicalUse", physicalUse);
        return data;
    }

    /**
     * 获取当前运行的参数指标
     *
     * @return
     */
    public static Map<String, Object> getJvmRunningState() {


        Runtime rt = Runtime.getRuntime();
        long vmTotal = rt.totalMemory() / byteToMb;
        long vmFree = rt.freeMemory() / byteToMb;
        long vmMax = rt.maxMemory() / byteToMb;
        long vmUse = vmTotal - vmFree;
        OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long physicalFree = mxBean.getFreePhysicalMemorySize() / byteToMb;
        long physicalTotal = mxBean.getTotalPhysicalMemorySize() / byteToMb;
        Map<String, Object> data = new HashMap<>(16);
        Properties properties = System.getProperties();

        data.put("javaVersion", properties.getProperty("java.vm.version"));
        data.put("jvmVendor", properties.getProperty("java.vm.vendor"));
        data.put("jvmUse", vmUse);
        data.put("jvmFree", vmFree);
        data.put("jvmTotal", vmTotal);
        data.put("jvmMax", vmMax);
        return data;

    }

    /**
     * 获取网卡速率(Linux下)
     * Windows不知道怎么实现
     * 干脆告诉他 项目不建议在win下运行，直接上linux
     *
     * @return
     */

    public static Map<String, Object> getNetworkState() {
        Map<String, Object> result = new HashMap<>();
        float TotalBandwidth = 1000;
        float netUsage = 0.0f;
        Process pro1, pro2;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/net/dev";
            //第一次采集流量数据
            long startTime = System.currentTimeMillis();
            pro1 = r.exec(command);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String line = null;
            long inSize1 = 0, outSize1 = 0;
            while ((line = in1.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("eth0")) {
                    System.out.println(line);
                    String[] temp = line.split("\\s+");
                    inSize1 = Long.parseLong(temp[1].substring(5)); //Receive bytes,单位为Byte
                    outSize1 = Long.parseLong(temp[9]);             //Transmit bytes,单位为Byte
                    break;
                }
            }
            in1.close();
            pro1.destroy();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                result.put("netUsage", 0);
                result.put("currentRate", 0);
                return result;
            }
            //第二次采集流量数据
            long endTime = System.currentTimeMillis();
            pro2 = r.exec(command);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            long inSize2 = 0, outSize2 = 0;
            while ((line = in2.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("eth0")) {
                    System.out.println(line);
                    String[] temp = line.split("\\s+");
                    inSize2 = Long.parseLong(temp[1].substring(5));
                    outSize2 = Long.parseLong(temp[9]);
                    break;
                }
            }
            if (inSize1 != 0 && outSize1 != 0 && inSize2 != 0 && outSize2 != 0) {
                // 大概2S一次
                float interval = (float) (endTime - startTime) / 1000;
                //网口传输速度,单位为bps
                float currentRate = (float) (inSize2 - inSize1 + outSize2 - outSize1) * 8 / (1000000 * interval);
                netUsage = currentRate / TotalBandwidth;
                result.put("netUsage", netUsage);
                result.put("currentRate", currentRate);

            }
            in2.close();
            pro2.destroy();
            return result;
        } catch (IOException e) {
            result.put("netUsage", 0);
            result.put("currentRate", 0);
            return result;
        }
    }
}
