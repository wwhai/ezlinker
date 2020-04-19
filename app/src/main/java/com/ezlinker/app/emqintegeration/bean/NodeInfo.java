package com.ezlinker.app.emqintegeration.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NodeInfo {
    private Float processUsed;
    private Float processAvailable;
    private Float memoryUsed;
    private Float memoryTotal;
    private Float load5;
    private Float load15;
    private Float load1;
    private LocalDateTime createTime;
}
