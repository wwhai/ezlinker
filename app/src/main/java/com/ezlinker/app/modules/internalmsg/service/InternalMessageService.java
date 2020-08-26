package com.ezlinker.app.modules.internalmessage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ezlinker.app.modules.internalmessage.model.InternalMessage;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lc5900
 * @since 2019-11-13
 */
public interface InternalMessageService {
    /**
     * @param entity
     */
    void save(InternalMessage entity);

    /**
     * 删除
     *
     * @param ids
     */
    void removeByIds(Integer[] ids);

    /**
     * 标记
     *
     * @param id
     */
    void mark(String id);

    /**
     * 获取一项
     * @param id
     * @return
     */
    InternalMessage getOne(String id);

    /**
     * 从MongoDB查询站内信
     *
     * @param userId 用户
     * @param marked 是否标记阅读
     * @return
     */
    IPage<InternalMessage> queryForPage(Long userId, Integer marked, Pageable pageable);

    /**
     * 计算消息总数
     * @param userId
     * @return
     */
    long count(Long userId);
}
