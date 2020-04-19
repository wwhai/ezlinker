package com.ezlinker.app.modules.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 项目
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_project")
public class Project extends XEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 产品开发中
     */
    public static final int DEVELOPING = 0;
    /**
     * 生产中
     */
    public static final int PRODUCING = 1;

    /**
     * 自由项目
     */
    public static final int FREE = 1;
    /**
     * 批量项目
     */
    public static final int BATCH = 2;

    @NotEmpty(message = "项目名称不可为空")
    private String name;

    private String logo;

    private String description;

    private String location;

    private Integer state = DEVELOPING;


    /**
     * 项目类型:
     * 1 :自由项目
     * 2 :批量项目
     */
    private Integer type = FREE;
}
