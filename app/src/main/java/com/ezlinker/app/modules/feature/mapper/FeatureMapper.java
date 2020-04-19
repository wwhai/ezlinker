package com.ezlinker.app.modules.feature.mapper;

import com.ezlinker.app.modules.feature.model.Feature;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 产品的功能（特性），和设备是多对多的关系，通过中间表关联起来 Mapper 接口
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
public interface FeatureMapper extends BaseMapper<Feature> {

}
