package com.ezlinker.app.modules.internalmessage.controller;

import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.internalmessage.model.InternalMessage;
import com.ezlinker.app.modules.internalmessage.service.InternalMessageService;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 站内信 前段控制器
 * </p>
 *
 * @author lc5900
 * @since 2019-11-13
 */
@RestController
@RequestMapping("/internalMessages")
public class InternalMessageController extends CurdController<InternalMessage> {

    private InternalMessageService internalMessageService;

    public InternalMessageController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Autowired
    public void setInternalMessageService(InternalMessageService internalMessageService) {
        this.internalMessageService = internalMessageService;
    }

    /**
     * 删除站内信
     *
     * @param ids 站内信id
     * @return
     * @throws XException
     */
    @Override
    protected R delete(@PathVariable Integer[] ids) throws XException {
        internalMessageService.removeByIds(ids);
        return success();
    }


    /**
     * 分页检索
     *
     * @param current 页码：必传
     * @param size    页长：必传
     * @return
     * @throws XException
     */
    @GetMapping
    public R queryForPage(
            @RequestParam Integer current,
            @RequestParam Integer marked,
            @RequestParam Integer size) throws XException {
        Pageable pageable = PageRequest.of(current, size, Sort.by(Sort.Direction.DESC, "_id"));

        return data(internalMessageService.queryForPage(getUserDetail().getId(), marked, pageable));
    }

    /**
     * 标记站内信
     *
     * @param id
     * @return
     * @throws XException
     */
    @PutMapping("/mark/{id}")
    public R mark(@PathVariable String id) throws XException {
        InternalMessage internalMessage = internalMessageService.getOne(id);
        if (internalMessage == null) {
            throw new XException("InternalMessage not exists!", "站内信不存在");
        }
        internalMessageService.mark(id);
        return data(internalMessage);
    }
}
