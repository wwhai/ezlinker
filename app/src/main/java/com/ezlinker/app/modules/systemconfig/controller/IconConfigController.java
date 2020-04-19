package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.IconConfig;
import com.ezlinker.app.modules.systemconfig.service.IIconConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后端维持的图表库配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/systemConfig/icons")
public class IconConfigController extends CurdController<IconConfig> {

    @Resource
    IIconConfigService iIconConfigService;

    public IconConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * 获取图标,使用了一个免费图床:https://imgchr.com
     *
     * @return
     */
    @Override
    protected R all() {
        List<String> urls = new ArrayList<>();
        urls.add("https://imgchr.com/i/3fzasU");
        return data(urls);
    }

}

