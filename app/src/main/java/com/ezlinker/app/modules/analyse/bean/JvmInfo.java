package com.ezlinker.app.modules.analyse.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class JvmInfo {
    private Float jvmUse;

    private Float jvmFree;

    private Float jvmTotal;

    private Float jvmMax;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

}
