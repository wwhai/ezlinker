package com.ezlinker.app;

import com.alibaba.fastjson.JSONObject;
import com.ezlinker.app.common.exception.BizException;
import com.ezlinker.app.modules.emqx.monitor.EMQMonitorV4;

/**
 * @author wangwenhai
 * @date 2020/9/25
 * File description:
 */
public class EmqxApiTest {
    public static void main(String[] args) throws BizException {
        JSONObject result = EMQMonitorV4.getBrokersInfo("127.0.0.1",
                8081,
                "emqx@127.0.0.1",
                "admin", "public");
        assert result != null;
        assert "Running".equals(result.get("node_status").toString());

        JSONObject result1 = EMQMonitorV4.getClientInfo("127.0.0.1",
                8081,
                "Plain",
                "admin", "public");
        assert result1 != null;
        assert "Plain".equals(result1.getString("clientid"));
        System.out.println();

    }
}
