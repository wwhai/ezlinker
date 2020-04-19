package com.ezlinker.app.config.socketio;

import com.ezlinker.app.modules.module.pojo.DataArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ezlinker
 *
 * @author wangwenhai
 * @description 服务端消息
 * @create 2019-11-27 22:31
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class S2CMessage extends XWSMsg{
    private DataArea dataArea;

}