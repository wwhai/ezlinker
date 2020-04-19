package com.ezlinker.app.modules.user.service.impl;

import com.ezlinker.app.modules.user.model.UserProfile;
import com.ezlinker.app.modules.user.mapper.UserProfileMapper;
import com.ezlinker.app.modules.user.service.IUserProfileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户的详情 服务实现类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-12
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements IUserProfileService {

}
