package com.ezlinker.app.modules.devicelog;

import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangwenhai
 * @date 2020/9/18
 * File description:
 */
@RestController
@RequestMapping("/deviceLogs")
public class DeviceLogController {
    @Resource
    DeviceLogService deviceLogService;

    /**
     * 获取用户的登录日志
     *
     * @return
     * @throws XException
     */
    @GetMapping
    public R queryForPage(@RequestParam(required = false) String clientId,
                          @RequestParam(required = false, defaultValue = "1") Integer current,
                          @RequestParam(required = false, defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "id"));
        return R.ok(deviceLogService.queryForPage(clientId, pageable));
    }
}
