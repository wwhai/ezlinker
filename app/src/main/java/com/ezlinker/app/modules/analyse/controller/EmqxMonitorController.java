package com.ezlinker.app.modules.analyse.controller;

import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.emqx.monitor.EMQMonitorV4;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description EMQX监控器
 * @create 2019-12-17 22:06
 **/
@RestController
@RequestMapping("/monitor/emqx")

public class EmqxMonitorController extends CurdController<EmqxConfig> {
    @Resource
    RedisUtil redisUtil;

    public EmqxMonitorController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * {
     * "nodeName": "emqx@node1.emqx.io",
     * "mqttPort": "1801",
     * "apiPort": "18881",
     * "appId": "admin",
     * "secret": "public"
     * }
     *
     * @return
     */
    @GetMapping("/all")
    @Override
    protected R all() {
        List<Map<Object, Object>> emqxList = new ArrayList<>();
        Set<Object> emqxNodeSet = redisUtil.sGet(RedisKeyPrefix.EMQX_NODE_NAME);
        for (Object o : emqxNodeSet) {
            Map<Object, Object> cache = redisUtil.hmget(RedisKeyPrefix.EMQX_NODE_STATE + o.toString());
            try {
                JSONObject result = EMQMonitorV4.getBrokersInfo(cache.get("ip").toString(),
                        Integer.parseInt(cache.get("apiPort").toString()),
                        cache.get("nodeName").toString(),
                        cache.get("appId").toString(),
                        cache.get("secret").toString());
                cache.put("detail", result);
                emqxList.add(cache);
            } catch (BizException e) {
                cache.put("detail", null);
            }
        }
        return data(emqxList);
    }
    //
//    /**
//     * 获取所有的EMQX配置
//     * 这个接口采取HTTP轮训的形式 5秒请求一次
//     *
//     * @return
//     */
//    @GetMapping("/all")
//    protected R all() {
//        List<EmqxConfig> emqxConfigList = iEmqxConfigService.list();
//        for (EmqxConfig config : emqxConfigList) {
//            Query query = new Query();
//            Criteria criteria = Criteria.where("node").is(config.getNodeName());
//            query.addCriteria(criteria);
//            query.with(Sort.by(Sort.Direction.DESC, "_id"));
//            List<NodeInfo> dataList = mongoTemplate.find(query, NodeInfo.class, "emqx_running_log_" + config.getNodeName());
//            /**
//             *  "runningState": {
//             *      "load1": "0.52",
//             *      "load5": "0.58",
//             *      "load15": "0.59"
//             *      "processAvailable": 2097152,
//             *      "processUsed": 446,
//             *      "memoryTotal": 172707840,
//             *      "memoryUsed": 112341880,
//             *      "createTime":
//             *  }
//             */
//            HashMap<String, Object> historyRunningState = new LinkedHashMap<>();
//            //
//            List<Float> load1 = new ArrayList<>();
//            List<Float> load5 = new ArrayList<>();
//            List<Float> load15 = new ArrayList<>();
//            List<Float> processAvailable = new ArrayList<>();
//            List<Float> processUsed = new ArrayList<>();
//            List<Float> memoryTotal = new ArrayList<>();
//            List<Float> memoryUsed = new ArrayList<>();
//            List<Object> createTime = new ArrayList<>();
//            //
//            for (NodeInfo info : dataList) {
//                load1.add(info.getLoad1());
//                load5.add(info.getLoad5());
//                load15.add(info.getLoad15());
//                processAvailable.add(info.getProcessAvailable());
//                processUsed.add(info.getProcessUsed());
//                memoryTotal.add(info.getMemoryTotal());
//                memoryUsed.add(info.getMemoryUsed());
//                createTime.add(info.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            }
//            historyRunningState.put("node", config.getNodeName());
//            historyRunningState.put("load1", load1);
//            historyRunningState.put("load5", load5);
//            historyRunningState.put("load15", load15);
//            historyRunningState.put("processAvailable", processAvailable);
//            historyRunningState.put("processUsed", processUsed);
//            historyRunningState.put("memoryTotal", memoryTotal);
//            historyRunningState.put("memoryUsed", memoryUsed);
//            historyRunningState.put("createTime", createTime);
////            //
////            HashMap<String, Object> currentRunningState = new LinkedHashMap<>();
////            JSONObject currentNodeInfo;
////            try {
////                // 这里获取到的数据是EMQ直接返回的 下划线格式
////                currentNodeInfo = EMQMonitorV4.getNodeInfo(config.getIp(), config.getApiPort(), config.getNodeName(), config.getAppId(), config.getSecret());
////                // 如果是离线 就更新为在线
////                if (currentNodeInfo != null) {
////                    currentRunningState.put("node", currentNodeInfo.getString("node"));
////                    currentRunningState.put("load1", currentNodeInfo.getFloat("load1"));
////                    currentRunningState.put("load5", currentNodeInfo.getFloat("load5"));
////                    currentRunningState.put("load15", currentNodeInfo.getFloat("load15"));
////                    currentRunningState.put("processAvailable", currentNodeInfo.getFloat("process_available"));
////                    currentRunningState.put("processUsed", currentNodeInfo.getFloat("process_used"));
////                    currentRunningState.put("memoryTotal", currentNodeInfo.getFloat("memory_total"));
////                    currentRunningState.put("memoryUsed", currentNodeInfo.getFloat("memory_used"));
////                    currentRunningState.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
////                }
////
////            } catch (Exception e) {
////                //e.printStackTrace();
////                logger.error(e.getMessage());
////            }
//            //
//
//        }
//        return data(emqxConfigList);
//    }

}
