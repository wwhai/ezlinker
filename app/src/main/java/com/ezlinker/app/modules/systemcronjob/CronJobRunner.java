package com.ezlinker.app.modules.systemcronjob;

import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.utils.OSMonitor;
import com.ezlinker.app.modules.constant.MongoCollectionPrefix;
import com.ezlinker.app.modules.emqx.monitor.EMQMonitorV4;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import com.ezlinker.app.modules.systemconfig.service.IEmqxConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

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

        mongoTemplate.insert(data, MongoCollectionPrefix.SYSTEM_OS_LOG);
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
                // String ip, String apiPort, String nodeName, String appId, String secret
                runningState = EMQMonitorV4.getNodeInfo(config.getIp(), config.getApiPort(), config.getNodeName(), config.getAppId(), config.getSecret());
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
                    mongoTemplate.insert(data, MongoCollectionPrefix.EMQX_RUNNING_LOG + "_" + config.getNodeName());
                }
            } catch (BizException e) {
                logger.error(e.getMessage());
            }

        }
        logger.info("Cron EMQX node running data finished");
    }

    /**
     * 5s一次同步状态
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void cronEmqxState() {
        List<EmqxConfig> emqxConfigList = iEmqxConfigService.list();
        for (EmqxConfig config : emqxConfigList) {
            JSONObject runningState;
            try {
                // String ip, String apiPort, String nodeName, String appId, String secret
                runningState = EMQMonitorV4.getBrokersInfo(config.getIp(), config.getApiPort(), config.getNodeName(), config.getAppId(), config.getSecret());
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
                    mongoTemplate.insert(data, MongoCollectionPrefix.SYSTEM_OS_LOG + "_" + config.getNodeName());
                }
            } catch (BizException e) {
                logger.error(e.getMessage());
            }

        }
    }

}