package com.ezlinker.app.modules.systemlog.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 整个系统内部的运行日志
 */
@Data
public class SystemLog {
    private int type = 0;
    private String who;
    private String operation;
    private String message;
    private LocalDateTime createTime;

    public interface SystemLogType {
        int normal = 0;
        int warn = 1;
        int error = 2;
    }
}
