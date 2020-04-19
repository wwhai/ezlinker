package com.ezlinker.app.modules.module.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wangwenhai
 * @date 2020/2/16
 * File description:
 */
@Data
@Accessors(chain = true)
public class ModuleData {
    @JsonIgnore
    private Long moduleId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-mm-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date createTime;

    /**
     * 数据
     */
    private Object data;

}
