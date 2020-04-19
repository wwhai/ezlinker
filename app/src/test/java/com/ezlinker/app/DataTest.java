package com.ezlinker.app;

import com.ezlinker.app.modules.dataentry.model.DeviceData;
import com.ezlinker.app.modules.dataentry.service.DeviceDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest

public class DataTest {
    @Resource
    DeviceDataService deviceDataService;

    public static void main(String[] args) throws ParseException {
        Query query = new Query();
        //2020-04-19T10:30:00.003Z
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        query.addCriteria(Criteria.where("createTime")
                .lte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end.format(dateTimeFormatter)))
                .gte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start.format(dateTimeFormatter))));
        System.out.println(query.toString());
    }

    @Test
    public void addData() {

        for (int i = 0; i < 1005; i++) {
            DeviceData deviceData = new DeviceData();
            deviceData.setDeviceId(3L);
            deviceData.setCreateTime(new Date());
            Map<String, Object> data = new HashMap<>();
            data.put("数值", new Random().nextDouble());
            data.put("备注", new Random().nextFloat());
            deviceData.setData(data);
            deviceDataService.save(1L, 3L, deviceData);

        }


    }
}
