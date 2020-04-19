package com.ezlinker.app.modules.tag.service.impl;

import com.ezlinker.app.modules.tag.model.Tag;
import com.ezlinker.app.modules.tag.mapper.TagMapper;
import com.ezlinker.app.modules.tag.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备Tag表，用来给设备绑定多个tag 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-24
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
