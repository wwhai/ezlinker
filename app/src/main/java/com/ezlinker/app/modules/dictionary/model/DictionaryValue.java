package com.ezlinker.app.modules.dictionary.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 字典的值
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_dictionary_value")
public class DictionaryValue extends XEntity {

    private static final long serialVersionUID=1L;

    @NotNull(message = "字典项不可为空")
    private Long keyId;
    /**
     * 名称
     */
    @NotNull(message = "字典值不可为空")

    private String value;

    /**
     * 显示的文本
     */
    @NotNull(message = "字典值标签不可为空")

    private String label;


}
