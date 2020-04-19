package com.ezlinker.app.modules.relation.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_relation_device_module")
public class RelationDeviceModule extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备
     */
    private Integer deviceId;

    /**
     *  模块
     */
    private Integer moduleId;


    public static final String DEVICE_ID = "device_id";

    public static final String MODULE_ID = "module_id";

}
