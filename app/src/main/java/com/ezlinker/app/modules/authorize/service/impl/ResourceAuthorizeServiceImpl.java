package com.ezlinker.app.modules.authorize.service.impl;

import com.ezlinker.app.modules.authorize.model.ResourceAuthorize;
import com.ezlinker.app.modules.authorize.mapper.ResourceAuthorizeMapper;
import com.ezlinker.app.modules.authorize.service.IResourceAuthorizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源授权表,记录所有存在权限关系的双方 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-05
 */
@Service
public class ResourceAuthorizeServiceImpl extends ServiceImpl<ResourceAuthorizeMapper, ResourceAuthorize> implements IResourceAuthorizeService {

}
