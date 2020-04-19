package com.ezlinker.app.modules.relation.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * XXX协议支持的XXX类型的模块关系表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_relation_protocol_type_module_type")
public class RelationProtocolTypeModuleType extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 协议类型
     */
    private Integer protocolTypeId;

    /**
     * 模块类型
     */
    private Integer moduleTypeId;


}
