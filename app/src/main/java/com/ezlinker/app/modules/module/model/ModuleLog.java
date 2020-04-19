package com.ezlinker.app.modules.module.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ezlinker
 * @description: 模块上下线记录
 * @author: wangwenhai
 * @create: 2019-12-19 16:19
 **/
@Data
public class ModuleLog implements Serializable {
    // 连接
    public static Integer CONNECT = 1;
    // 掉线
    public static Integer DISCONNECT = 2;

    private String id;
    private String sn;
    private String deviceName;
    private String moduleName;
    private Long moduleId;
    private Integer type;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

}
