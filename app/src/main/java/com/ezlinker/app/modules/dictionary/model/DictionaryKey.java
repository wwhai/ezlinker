package com.ezlinker.app.modules.dictionary.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 字典的项
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_dictionary_key")
public class DictionaryKey extends XEntity {

    private static final long serialVersionUID=1L;

    /**
     * 表名
     */
    @NotEmpty(message = "字典项所属表名不可为空")
    private String tableName;

    /**
     * 名称
     */
    @NotEmpty(message = "字典项名称不可为空")

    private String name;

    /**
     * 显示的文本
     */
    @NotEmpty(message = "字典项标签不可为空")

    private String label;


}
