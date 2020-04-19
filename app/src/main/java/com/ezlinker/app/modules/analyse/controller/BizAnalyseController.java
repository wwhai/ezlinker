package com.ezlinker.app.modules.analyse.controller;

import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.web.XController;
import com.ezlinker.app.modules.device.service.IDeviceService;
import com.ezlinker.app.modules.product.service.IProductService;
import com.ezlinker.app.modules.project.service.IProjectService;
import com.ezlinker.app.modules.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据统计
 *
 * @author wangwenhai
 */
@RestController
@RequestMapping("/bizAnalyse")
public class BizAnalyseController extends XController {
    public BizAnalyseController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Resource
    IProjectService iProjectService;

    @Resource
    IProductService iProductService;

    @Resource
    IUserService iUserService;
    @Resource
    IDeviceService iDeviceService;

    /**
     * 获取一些统计数据
     * TODO: 后期会加入更多统计数据
     *
     * @return
     */

    @GetMapping("/overView")
    public R overView() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("projects", iProjectService.count());
        data.put("users", iUserService.count());
        data.put("products", iProductService.count());
        data.put("devices", iDeviceService.count());

        return data(data);
    }


}
