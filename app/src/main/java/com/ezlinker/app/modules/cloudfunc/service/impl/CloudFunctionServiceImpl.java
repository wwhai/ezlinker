package com.ezlinker.app.modules.cloudfunc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.cloudfunc.mapper.CloudFunctionMapper;
import com.ezlinker.app.modules.cloudfunc.model.CloudFunction;
import com.ezlinker.app.modules.cloudfunc.service.ICloudFunctionService;
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
