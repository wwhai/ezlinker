package com.ezlinker.app;

import com.ezlinker.app.modules.device.pojo.FieldParam;
import com.ezlinker.app.modules.device.service.IDeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangwenhai
 * @date 2020/9/27
 * File description:
 */
@SpringBootTest
class FieldParamTest {
    @Resource
    IDeviceService iDeviceService;

    @Test
    void testGetJson() {

        List<FieldParam> fieldParams = iDeviceService.getFieldParams("186876e771dc123bfebb4fff2bc7cb75");
        System.out.println(fieldParams);
    }
}
