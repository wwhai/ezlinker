package com.ezlinker.app.modules.tag.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备Tag表，用来给设备绑定多个tag
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_tag")
public class Tag extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Tagname
     */
    private String name;

    /**
     * Tag 作用的对象ID
     */
    private Long linkId;


}
