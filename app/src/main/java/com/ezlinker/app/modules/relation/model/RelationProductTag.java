package com.ezlinker.app.modules.relation.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Tag 产品关联
 * </p>
 *
 * @author wangwenhai
 * @since 2019-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_relation_product_tag")
public class RelationProductTag extends XEntity {

    private static final long serialVersionUID=1L;

    private Long tagId;

    private Long productId;


    public static final String TAG_ID = "tag_id";

    public static final String PRODUCT_ID = "product_id";

}
