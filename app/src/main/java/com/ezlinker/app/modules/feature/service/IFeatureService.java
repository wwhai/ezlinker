package com.ezlinker.app.modules.feature.service;

import com.ezlinker.app.modules.feature.model.Feature;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品的功能（特性），和设备是多对多的关系，通过中间表关联起来 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
public interface IFeatureService extends IService<Feature> {

}
