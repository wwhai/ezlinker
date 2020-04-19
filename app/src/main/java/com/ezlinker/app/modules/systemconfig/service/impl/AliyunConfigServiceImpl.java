package com.ezlinker.app.modules.systemconfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezlinker.app.modules.systemconfig.mapper.AliyunConfigMapper;
import com.ezlinker.app.modules.systemconfig.model.AliyunConfig;
import com.ezlinker.app.modules.systemconfig.service.IAliyunConfigService;
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
public class AliyunConfigServiceImpl extends ServiceImpl<AliyunConfigMapper, AliyunConfig> implements IAliyunConfigService {

}
