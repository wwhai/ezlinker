package com.ezlinker.app.modules.systemcronjob;

import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.utils.OSMonitor;
import com.ezlinker.app.emqintegeration.monitor.EMQMonitorV4;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import com.ezlinker.app.modules.systemconfig.service.IEmqxConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统的定时任务策略
 */
//@Component
public class CronJobRunner {
    private static Logger logger = LoggerFactory.getLogger(CronJobRunner.class);
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    IEmqxConfigService iEmqxConfigService;

    /**
     * 定时在数据库插入系统OS状态
     * 5分钟一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @Async

    public void cronOSRunningData() {
        logger.info("Start cron OS running data");
        Map<String, Object> data = new LinkedHashMap<>();

        Map<String, Object> running = OSMonitor.getOSInfo();
        data.put("physicalFree", running.get("physicalFree"));
        data.put("physicalTotal", running.get("physicalTotal"));
        data.put("physicalUse", running.get("physicalUse"));
        data.put("createTime", LocalDateTime.now());

        mongoTemplate.insert(data, LogTableName.SYSTEM_OS_STATE);
        logger.info("Cron OS running data finished");
    }

    /**
     * 定时在数据库插入JVM状态
     * 2分钟一次
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    @Async

    public void cronJVMRunningData() {
        logger.info("Start cron JVM running data");
        Map<String, Object> running = OSMonitor.getJvmRunningState();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("jvmUse", running.get("jvmUse"));
        data.put("jvmFree", running.get("jvmFree"));
        data.put("jvmTotal", running.get("jvmTotal"));
        data.put("jvmMax", running.get("jvmMax"));
        data.put("createTime", LocalDateTime.now());

        mongoTemplate.insert(data, LogTableName.JVM_STATE);
        logger.info("Cron JVM running data finished");
    }

    /**
     * 定时在数据库插入网卡状态
     * 1分钟一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    @Async

    public void cronNetworkRunningData() {
        logger.info("Start cron OS running data");
        Map<String, Object> running = OSMonitor.getNetworkState();
        mongoTemplate.insert(running, LogTableName.SYSTEM_NETWORK_STATE);
        logger.info("Cron OS running data finished");
    }

    /**
     * 数据库定时插入EMQ节点的运行数据
     * 一分钟一条
     */

    @Scheduled(cron = "0 0/2 * * * ?")
    @Async
    public void cronEmqNodeRunningData() {
        logger.info("Start cron EMQX node running data");
        List<EmqxConfig> emqxConfigList = iEmqxConfigService.list();
        for (EmqxConfig config : emqxConfigList) {
            JSONObject runningState;
            try {
                runningState = EMQMonitorV4.getNodeInfo(config);
                if (runningState != null) {
                    Map<String, Object> data = new LinkedHashMap<>();
                    data.put("node", config.getNodeName());
                    data.put("load1", runningState.get("load1"));
                    data.put("load5", runningState.get("load5"));
                    data.put("load15", runningState.get("load15"));
                    data.put("processAvailable", runningState.get("process_available"));
                    data.put("processUsed", runningState.get("process_used"));
                    data.put("memoryTotal", runningState.get("memory_total"));
                    data.put("memoryUsed", runningState.get("memory_used"));
                    data.put("createTime", LocalDateTime.now());
                    mongoTemplate.insert(data, LogTableName.EMQX_RUNNING_LOG + "_" + config.getNodeName());
                }
            } catch (BizException e) {
                logger.error(e.getMessage());
            }

        }
        logger.info("Cron EMQX node running data finished");
    }

    /**
     * 定时清空日志记录
     * 0 0 0 1/7 * ?
     * 每周清理一次
     */

    @Scheduled(cron = "0 0 0 1/7 * ?")
    @Async
    public void cronClearLog() {
        logger.info("Start clear system log");

        mongoTemplate.dropCollection(LogTableName.EMQX_RUNNING_LOG);
        mongoTemplate.dropCollection(LogTableName.SYSTEM_EVENT_LOG);
        mongoTemplate.dropCollection(LogTableName.SYSTEM_OS_STATE);
        mongoTemplate.dropCollection(LogTableName.JVM_STATE);
        mongoTemplate.dropCollection(LogTableName.SYSTEM_NETWORK_STATE);

        logger.info("Clear system log finished");
    }

    /**
     * 这个接口记录一些数据库日志表名字常量
     */
    interface LogTableName {

        // 系统事件表
        String SYSTEM_EVENT_LOG = "system_event_log";
        // EMQX 节点运行日志表
        String EMQX_RUNNING_LOG = "emqx_running_log";
        // 操作系统系统所有运行日志
        String SYSTEM_OS_STATE = "system_os_log";
        // 网络日志
        String SYSTEM_NETWORK_STATE = "system_network_log";
        // JVM运行日志
        String JVM_STATE = "jvm_state_log";

    }


}
