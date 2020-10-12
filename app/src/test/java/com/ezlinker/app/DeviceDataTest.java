package com.ezlinker.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ezlinker.app.modules.dataentry.model.DeviceData;
import com.ezlinker.app.modules.dataentry.service.DeviceDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * @author wangwenhai
 * @date 2020/10/7
 * File description:
 */
@SpringBootTest
class DeviceDataTest {
    @Resource
    DeviceDataService deviceDataService;

    @Test
    void insertData() {

        for (int i = 0; i < 30; i++) {
            DeviceData deviceData = new DeviceData();
            deviceData.setClientId("ad961d6b3a287cb435346e42326ab2bf");
            deviceData.setData("test-" + i);
            deviceDataService.save("ad961d6b3a287cb435346e42326ab2bf", deviceData);

        }

    }

    @Test
    void listData() {
        IPage<DeviceData> deviceDataPage = deviceDataService.queryForPage("ad961d6b3a287cb435346e42326ab2bf", PageRequest.of(0, 10));
        assert deviceDataPage.getRecords().size() == 10;
        assert deviceDataPage.getTotal() == 104;
        assert deviceDataPage.getCurrent() == 0;
    }

}
