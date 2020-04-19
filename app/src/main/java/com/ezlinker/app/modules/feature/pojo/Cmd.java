package com.ezlinker.app.modules.feature.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2019/12/28
 * File description: 指令,WEB端推送的时候, 需要封装的指令格式
 * 其中 cmdKey:为终端接受的命令
 * cmdValues:为命令扩展参数
 */

@Data
public class Cmd {
    /**
     * 命令Key
     */
    @NotEmpty(message = "指令名不可为空值")
    private String cmdKey;

    /**
     * 命令Value
     */
    private List<HashMap<String, Object>> cmdValues;

}
