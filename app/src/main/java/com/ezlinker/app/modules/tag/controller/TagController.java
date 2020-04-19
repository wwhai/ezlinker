package com.ezlinker.app.modules.tag.controller;

import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.tag.model.Tag;
import com.ezlinker.app.modules.tag.service.ITagService;
import com.ezlinker.app.common.exception.XException;
import com.ezlinker.app.common.exchange.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wangwenhai
 * @date 2019/12/14
 * File description: Tag
 */
@RestController
@RequestMapping("/tags")
public class TagController extends CurdController<Tag> {
    @Resource
    ITagService iTagService;

    public TagController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    protected R add(Tag tag) throws XException {

        return data(iTagService.save(tag));
    }


    /**
     * 获取所有的tags
     *
     * @return
     */


    @GetMapping("/")
    public R queryForPage() {

        return data(iTagService.list());
    }
}
