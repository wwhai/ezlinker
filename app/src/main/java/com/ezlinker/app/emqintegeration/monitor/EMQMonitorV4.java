package com.ezlinker.app.emqintegeration.monitor;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.modules.systemconfig.model.EmqxConfig;

public class EMQMonitorV4 {
    /**
     * 获取节点基本信息¶
     * GET api/v4/nodes/emqx@127.0.0.1
     *
     * @return
     */
    public static JSONObject getNodeInfo(EmqxConfig emqxConfig) throws BizException {
        try {
            String body = HttpRequest.get("http://" + emqxConfig.getIp() + ":" + emqxConfig.getPort() + "/api/v4/nodes/" + emqxConfig.getNodeName())
                    .basicAuth(emqxConfig.getAppId(), emqxConfig.getSecret())
                    .timeout(2000)
                    .execute()
                    .body();
            if (body != null) {
                JSONObject data = JSON.parseObject(body);
                if (data.getIntValue("state") == 0) {
                    return data.getJSONObject("data");
                }
            }

        } catch (Exception e) {
            throw new BizException("EMQX节点:" + emqxConfig.getNodeName() + " 连接失败!", "Node:" + emqxConfig.getNodeName() + " connect failure!");

        }
        throw new BizException("EMQX节点:" + emqxConfig.getNodeName() + " 连接失败!", "Node:" + emqxConfig.getNodeName() + " connect failure!");

    }

    /**
     * @param emqxConfig
     * @return
     * @throws BizException
     */
    public static JSONObject getBrokersInfo(EmqxConfig emqxConfig) throws BizException {
        try {
            String body = HttpRequest.get("http://" + emqxConfig.getIp() + ":" + emqxConfig.getPort() + "/api/v4/brokers/" + emqxConfig.getNodeName())
                    .basicAuth(emqxConfig.getAppId(), emqxConfig.getSecret())
                    .timeout(2000)
                    .execute()
                    .body();
            if (body != null) {
                JSONObject data = JSON.parseObject(body);
                if (data.getIntValue("state") == 0) {
                    return data.getJSONObject("data");
                }
            }

        } catch (Exception e) {
            throw new BizException("EMQX节点:" + emqxConfig.getNodeName() + " 连接失败!", "Node:" + emqxConfig.getNodeName() + " connect failure!");

        }
        throw new BizException("EMQX节点:" + emqxConfig.getNodeName() + " 连接失败!", "Node:" + emqxConfig.getNodeName() + " connect failure!");

    }

}
