package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备协议配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_device_protocol_config")
public class DeviceProtocolConfig extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标签
     */
    private String label;

    /**
     * 名称
     */
    private String name;

    /**
     * 配置值
     */
    private Integer value;

    /**
     * 描述
     */
    private String description;


}
