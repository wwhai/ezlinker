package com.ezlinker.app.modules.emqx.monitor;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;

public class EMQMonitorV4 {
    /**
     * @param ip
     * @param apiPort
     * @param nodeName
     * @param appId
     * @param secret
     * @return
     * @throws BizException
     */
    public static JSONObject getNodeInfo(String ip, Integer apiPort, String nodeName, String appId, String secret) throws BizException {
        try {
            String body = HttpRequest.get("http://" + ip + ":" + apiPort + "/api/v4/nodes/" + nodeName)
                    .basicAuth(appId, secret)
                    .timeout(2000)
                    .execute()
                    .body();
            JSONObject data = JSON.parseObject(body);
            return data.getJSONObject("data");
        } catch (Exception e) {
            throw new BizException("EMQX节点:" + nodeName + " 连接失败!", "Node:" + nodeName + " connect failure!");
        }
    }

    /**
     * @param ip
     * @param apiPort
     * @param nodeName
     * @param appId
     * @param secret
     * @return
     * @throws BizException
     */
    public static JSONObject getBrokersInfo(String ip, Integer apiPort, String nodeName, String appId, String secret) throws BizException {
        try {
            String body = HttpRequest.get("http://" + ip + ":" + apiPort + "/api/v4/brokers/" + nodeName)
                    .basicAuth(appId, secret)
                    .timeout(2000)
                    .execute()
                    .body();
            JSONObject data = JSON.parseObject(body);
            return data.getJSONObject("data");
        } catch (Exception e) {
            throw new BizException("EMQX节点:" + nodeName + " 连接失败!", "Node:" + nodeName + " connect failure!");
        }
    }

    /**
     * 获取某客户端的信息
     * {
     * "data":[
     * {
     * "recv_cnt":1,
     * "mqueue_len":0,
     * "max_awaiting_rel":100,
     * "send_msg":0,
     * "proto_ver":4,
     * "is_bridge":false,
     * "clientid":"Plain",
     * "recv_pkt":1,
     * "awaiting_rel":0,
     * "recv_msg":0,
     * "recv_oct":33,
     * "expiry_interval":0,
     * "connected":true,
     * "send_pkt":0,
     * "heap_size":1598,
     * "clean_start":true,
     * "zone":"external",
     * "connected_at":"2020-09-25 15:15:05",
     * "inflight":0,
     * "node":"emqx@127.0.0.1",
     * "username":"plain",
     * "keepalive":60,
     * "reductions":8189,
     * "mailbox_len":0,
     * "send_cnt":0,
     * "mqueue_dropped":0,
     * "created_at":"2020-09-25 15:15:05",
     * "max_subscriptions":0,
     * "ip_address":"127.0.0.1",
     * "subscriptions_cnt":0,
     * "max_inflight":32,
     * "send_oct":0,
     * "proto_name":"MQTT",
     * "max_mqueue":1000,
     * "port":54554
     * }
     * ],
     * "code":0
     * }
     *
     * @param ip
     * @param apiPort
     * @param clientId
     * @param appId
     * @param secret
     * @return
     * @throws BizException
     */
    public static JSONObject getClientInfo(String ip, Integer apiPort, String clientId, String appId, String secret) throws BizException {
        try {
            String body = HttpRequest.get("http://" + ip + ":" + apiPort + "/api/v4/clients/" + clientId)
                    .basicAuth(appId, secret)
                    .timeout(2000)
                    .execute()
                    .body();
            JSONObject data = JSON.parseObject(body);
            JSONArray array = data.getJSONArray("data");
            if (array.size() == 1) {
                return (JSONObject) array.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BizException("EMQX节点:" + clientId + " 连接失败!", "ClientId:" + clientId + " failure!");
        }
    }

    /**
     * 强制离线,状态码参考：
     * <p>
     * https://docs.emqx.net/broker/latest/cn/advanced/http-api.html#返回码-result-codes
     *
     * @param ip
     * @param apiPort
     * @param clientId
     * @param appId
     * @param secret
     * @return
     * @throws BizException
     */

    public static Integer kickoff(String ip, Integer apiPort, String clientId, String appId, String secret) throws BizException {
        try {
            String body = HttpRequest.delete("http://" + ip + ":" + apiPort + "/api/v4/clients/" + clientId)
                    .basicAuth(appId, secret)
                    .timeout(2000)
                    .execute()
                    .body();
            JSONObject data = JSON.parseObject(body);
            return data.getInteger("code");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("EMQX节点:" + clientId + " 连接失败!", "ClientId:" + clientId + " failure!");
        }
    }
}
