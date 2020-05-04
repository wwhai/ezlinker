package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支持的固件
 * </p>
 *
 * @author wangwenhai
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_device_rom_support_config")
public class DeviceRomSupportConfig extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String description;


}
