package com.ezlinker.app.modules.dataentry.controller;

import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.emqintegeration.message.ConnectedMessage;
import com.ezlinker.app.emqintegeration.message.DisconnectedMessage;
import com.ezlinker.app.modules.module.service.IModuleService;
import com.ezlinker.app.modules.module.service.ModuleLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: ezlinker
 * @description: EMQ数据入口
 * @author: wangwenhai
 * @create: 2019-11-21 10:39
 **/
@RestController
@RequestMapping("/data")
public class AdvisoryController {

    @Resource
    IModuleService iModuleService;

    @Resource
    ModuleLogService moduleLogService;

    /**
     * 上线回调
     *
     * @param message
     * @return
     * @throws XException
     */
    @PostMapping("/connected")
    public R connected(@RequestBody @Valid ConnectedMessage message) throws XException {
        System.out.println("设备 clientId is:" + message.getClientId() + " Username is:" + message.getUsername() + " 上线");
        return new R();
    }

    /**
     * 模块离线回调
     *
     * @param message
     * @return
     * @throws XException
     */
    @PostMapping("/disconnected")
    public R disconnected(@RequestBody @Valid DisconnectedMessage message) throws XException {
        System.out.println("设备 clientId is:" + message.getClientId() + " Username is:" + message.getUsername() + " 下线");
        return new R();
    }


}
