package com.ezlinker.app.modules.systemconfig.service.impl;

import com.ezlinker.app.modules.systemconfig.model.WxAppConfig;
import com.ezlinker.app.modules.systemconfig.mapper.WxAppConfigMapper;
import com.ezlinker.app.modules.systemconfig.service.IWxAppConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信小程序密钥配置表 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-13
 */
@Service
public class WxAppConfigServiceImpl extends ServiceImpl<WxAppConfigMapper, WxAppConfig> implements IWxAppConfigService {

}
