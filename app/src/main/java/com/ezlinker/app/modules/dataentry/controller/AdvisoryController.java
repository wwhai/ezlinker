package com.ezlinker.app.modules.dataentry.controller;

import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.model.MongoObject;
import com.ezlinker.app.common.utils.RedisUtil;
import com.ezlinker.app.modules.constant.RedisKeyPrefix;
import com.ezlinker.app.modules.dataentry.model.AdvisoryMessage;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.ezlinker.app.modules.devicelog.DeviceLogService;
import com.ezlinker.app.modules.devicelog.DeviceLogType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ezlinker
 * @description: EMQ数据入口, EMQ的插件:https://github.com/emqx/emqx-web-hook
 * @author: wangwenhai
 * @create: 2019-11-21 10:39
 **/
@RestController
@RequestMapping("/deviceAdvisory")
public class AdvisoryController {

    @Resource
    RedisUtil redisUtil;
    @Resource
    DeviceLogService deviceLogService;
    @Resource
    IDeviceService iDeviceService;

    /**
     * 上线回调
     *
     * @param message
     * @return
     * @throws XException
     */
    @PostMapping("/onOffLine")
    public Mono<R> connected(@RequestHeader HashMap<String, Object> header, @RequestBody AdvisoryMessage message) {
        //TODO 检查Header携带的Token，开发阶段暂时不检查
        String type = message.getAction();
        checkHeader(header);

        // 每次设备上线的时候 刷新一下字段
        if (redisUtil.hmget(RedisKeyPrefix.DEVICE_FIELD_PARAMS + message.getClientid()).size() == 0) {
            for (FieldParam fieldParam : iDeviceService.getFieldParams(message.getClientid())) {
                redisUtil.hset(RedisKeyPrefix.DEVICE_FIELD_PARAMS + message.getClientid(), fieldParam.getField(), fieldParam.getField());
            }
        }
        // Redis存储的设备在线前缀
        switch (type) {
            case "client_connected":
                redisUtil.set(RedisKeyPrefix.DEVICE_ON_OFF_LINE_STATE + message.getClientid(), "ONLINE");
                if (!message.getClientid().startsWith("EZLINKER")) {
                    redisUtil.incr(RedisKeyPrefix.DEVICE_ONLINE_COUNT, 1);
                }
                MongoObject deviceLog1 = new MongoObject();
                deviceLog1.with("clientId", message.getClientid());
                deviceLog1.with("type", DeviceLogType.ONLINE);
                deviceLog1.with("detail", "设备[" + message.getClientid() + "]上线");
                deviceLogService.save(deviceLog1);
                break;
            case "client_disconnected":
                redisUtil.set(RedisKeyPrefix.DEVICE_ON_OFF_LINE_STATE + message.getClientid(), "OFFLINE");
                if (!message.getClientid().startsWith("EZLINKER")) {
                    Object count = redisUtil.get(RedisKeyPrefix.DEVICE_ONLINE_COUNT);
                    if (count != null && Integer.parseInt(count.toString()) > 0) {
                        redisUtil.decr(RedisKeyPrefix.DEVICE_ONLINE_COUNT, 1);
                    } else {
                        redisUtil.set(RedisKeyPrefix.DEVICE_ONLINE_COUNT, "0");
                    }
                }
                MongoObject deviceLog2 = new MongoObject();
                deviceLog2.with("clientId", message.getClientid());
                deviceLog2.with("type", DeviceLogType.OFFLINE);
                deviceLog2.with("detail", "设备[" + message.getClientid() + "]下线");
                deviceLogService.save(deviceLog2);
            default:
                break;
        }
        return Mono.just(R.ok());
    }

    /**
     * //检查Header
     *
     * @param header
     */
    private void checkHeader(Map<String, Object> header) {
        ///
    }

}
