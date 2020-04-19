package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.AliyunConfigMapper;
import com.ezlinker.app.modules.systemconfig.mapper.AliyunSmsConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.AliyunConfig;
import com.ezlinker.app.modules.systemconfig.model.AliyunSmsConfig;
import com.ezlinker.app.modules.systemconfig.service.IAliyunConfigService;
import com.ezlinker.app.modules.systemconfig.service.IAliyunSmsConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 阿里云配置表 服务实现类
 * </p>
 *
 * @author dropliu
 * @since 2020-03-09
 */
@Service
public class AliyunSmsConfigServiceImpl extends ServiceImpl<AliyunSmsConfigMapper, AliyunSmsConfig> implements IAliyunSmsConfigService {

}
