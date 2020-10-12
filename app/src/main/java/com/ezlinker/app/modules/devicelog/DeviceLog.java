package com.ezlinker.app.modules.devicelog;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wangwenhai
 * @date 2020/9/18
 * File description:
 */
@Data
public class DeviceLog {
    private String clientId;
    private DeviceLogType deviceLogType;
    private String detail;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createTime;
}
