package com.ezlinker.app.modules.cloudfunction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.cloudfunction.mapper.CloudFunctionMapper;
import com.ezlinker.app.modules.cloudfunction.model.CloudFunction;
import com.ezlinker.app.modules.cloudfunction.service.ICloudFunctionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 云函数 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-04-07
 */
@Service
public class CloudFunctionServiceImpl extends ServiceImpl<CloudFunctionMapper, CloudFunction> implements ICloudFunctionService {

}
