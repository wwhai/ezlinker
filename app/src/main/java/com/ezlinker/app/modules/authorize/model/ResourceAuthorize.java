package com.ezlinker.app.modules.authorize.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源授权表,记录所有存在权限关系的双方
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_resource_authorize")
public class ResourceAuthorize extends XEntity {
    /**
     * CURD 对应的值
     * -----------
     * 组合值:
     * 0 0 0 0 -- 0->无权限
     * 0 0 0 1 -- 1->D
     * 0 0 1 0 -- 2->R
     * 0 0 1 1 -- 3->R D
     * 0 1 0 0 -- 4->U
     * 0 1 0 1 -- 5->U D
     * 0 1 1 0 -- 6->U R
     * 0 1 1 1 -- 7->U R D
     * 1 0 0 0 -- 8->C
     * 1 0 0 1 -- 9->C D
     * 1 0 1 0 -- 10->C R
     * 1 0 1 1 -- 11->C R D
     * 1 1 0 0 -- 12->C U
     * 1 1 0 1 -- 13->C U D
     * 1 1 1 0 -- 14->C U R
     * 1 1 1 1 -- 15->C U R D
     * ------------
     */
    public static int C = 1;
    public static int U = 2;
    public static int R = 3;
    public static int D = 4;


    private static final long serialVersionUID = 1L;

    /**
     * 作用者
     */
    private String masterId;

    private String masterTable;


    /**
     * 被作用的对象
     */
    private Integer slaverId;
    private String slaverTable;

    /**
     * 授权值
     */
    private Integer authorizeValue = 0;


    public static final String MASTER_ID = "master_id";

    public static final String SLAVER_ID = "slaver_id";

    public static final String AUTHORIZE_VALUE = "authorize_value";

}
