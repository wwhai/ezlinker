package com.ezlinker.app.modules.userlog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 用户登录日志 服务类
 * </p>
 *
 * @author wangwenhai
 * @since 2019-11-12
 */
public interface IUserLoginLogService<UserLoginLog> {
    /**
     * 保存
     *
     * @param entity
     */
    void save(UserLoginLog entity);

    /**
     * 分页
     * @param userId
     * @param pageable
     * @return
     */
    IPage<UserLoginLog> queryForPage(Long userId, Pageable pageable);
}
