package com.ezlinker.app.modules.analyse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class OsInfo {
    private Float physicalFree;

    private Float physicalTotal;

    private Float physicalUse;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
}
