package com.ezlinker.app;

import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.device.model.Device;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author wangwenhai
 * @date 2020/9/14
 * File description:
 */
@SpringBootTest
class RedisTest {
    @Resource
    RedisUtil redisUtil;
    @Resource
    IDeviceService iDeviceService;

    @Test
    void hsetTest() {
        redisUtil.hset("device", "id", 1);
        Integer o = (Integer) redisUtil.hget("device", "id");
        assert o == 1;

    }

    @Test
    void testInitState() {
        Device device = iDeviceService.getById(1L);
        iDeviceService.initState(device);
    }

    @Test
    void testHget() {
        // EMQ集群部署最多允许7个节点
        Set<Object> set = redisUtil.sGet(RedisKeyPrefix.EMQX_NODE_NAME);
        for (Object o : set) {
            Object hget = redisUtil.hmget(RedisKeyPrefix.EMQX_NODE_STATE + o.toString());
            System.out.println("==>" + hget.toString());
        }
    }
}
