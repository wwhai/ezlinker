package com.ezlinker.app.modules.systemconfig.controller;


import com.ezlinker.app.common.web.CurdController;
import com.ezlinker.app.modules.systemconfig.model.VisualStyleConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 可视化视觉展示效果
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/systemconfig/visualStyleConfig")
public class VisualStyleConfigController extends CurdController<VisualStyleConfig> {

    public VisualStyleConfigController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }
}

