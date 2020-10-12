package com.ezlinker.app.modules.systemlog.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 整个系统内部的运行日志
 */
@Data
public class SystemLog {
    private SystemLogType type = SystemLogType.NORMAL;
    private String who;
    private String operation;
    private String message;
    private LocalDateTime createTime;

    public enum SystemLogType {
        NORMAL,
        WARN,
        ERROR;
    }
}
