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
@TableName("ez_relation_product_module")
public class RelationProductModule extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * 设备
     */
    private Long productId;

    /**
     *  模块
     */
    private Long moduleId;


    public static final String PRODUCT_ID = "product_id";

    public static final String MODULE_ID = "module_id";

}
