package com.ezlinker.app.modules.analyse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NetWorkInfo {
    Float netUsage, currentRate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

}
