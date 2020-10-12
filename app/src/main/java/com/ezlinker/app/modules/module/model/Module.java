package com.ezlinker.app.modules.module.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ezlinker.app.common.model.XEntity;
import com.ezlinker.app.modules.device.pojo.FieldParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 设备上面的模块，和设备是多对一关系
 * </p>
 *
 * @author wangwenhai
 * @since 2020-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "ez_module", autoResultMap = true)
public class Module extends XEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 名称
     */
    private String name;

    /**
     * 所属设备
     */
    private Long deviceId;

    /**
     * 型号
     */
    private String model;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 数据域
     * Size:最多不能超过20个属性
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<FieldParam> fieldParams;

    /**
     * 描述
     */
    private String description;
    /**
     * token
     */
    private String token;
    /**
     * 图标
     */
    private String icon;


}
