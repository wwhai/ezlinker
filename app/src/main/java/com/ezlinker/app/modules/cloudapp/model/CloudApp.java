package com.ezlinker.app.modules.cloudapp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 云APP
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_cloud_app")
public class CloudApp extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * User ID
     */
    private Integer userId;

    /**
     * UI显示的标签
     */
    private String name;

    private String description;


}
