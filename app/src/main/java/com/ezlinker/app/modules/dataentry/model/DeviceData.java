package com.ezlinker.app.modules.dataentry.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wangwenhai
 * @date 2020/2/16
 * File description: 设备数据
 */
@Data
@Accessors(chain = true)
public class DeviceData {
    private String clientId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-mm-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date createTime = new Date();

    /**
     * 数据
     */
    private Object data;


}
