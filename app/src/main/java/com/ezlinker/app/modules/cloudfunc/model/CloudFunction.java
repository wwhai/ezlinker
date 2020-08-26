package com.ezlinker.app.modules.cloudfunction.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 云函数
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_cloud_function")
public class CloudFunction extends XEntity {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long cloudAppId;
    /**
     * 创建人
     */
    private Long userId;
    /**
     * UI显示的标签
     */
    private String label;

    /**
     * 是否开启
     */
    private Boolean enable;
    /**
     * 描述文本
     */

    private String description;

    /**
     * Script content
     */
    private String script;


}
