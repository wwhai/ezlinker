package com.ezlinker.app.modules.feature.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.feature.mapper.FeatureMapper;
import com.ezlinker.app.modules.feature.model.Feature;
import com.ezlinker.app.modules.feature.service.IFeatureService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品的功能（特性），和设备是多对多的关系，通过中间表关联起来 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-13
 */
@Service
public class FeatureServiceImpl extends ServiceImpl<FeatureMapper, Feature> implements IFeatureService {


}
