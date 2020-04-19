package com.ezlinker.app.modules.analyse.controller;

import com.ezlinker.app.common.exchange.R;
import com.ezlinker.app.common.utils.OSMonitor;
import com.ezlinker.app.common.web.XController;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 统计操作系统 硬件信息
 */
@RestController
@RequestMapping("/monitor/os")
public class OSMonitorController extends XController {
    @Resource
    MongoTemplate mongoTemplate;

    public OSMonitorController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }


    @GetMapping("/currentState")
    public R currentOSRunningState() {
        return data(OSMonitor.getOSInfo());
    }


    @GetMapping("/running24h")
    public R osRunning24h() throws ParseException {
        // 时间轴
        List<Object> timeList = new ArrayList<>();
        // 数据轴
        List<Object> physicalFree = new ArrayList<>();
        List<Object> physicalTotal = new ArrayList<>();
        List<Object> physicalUse = new ArrayList<>();
        //
        Query query = new Query();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.addCriteria(Criteria.where("createTime")
                .lte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end.format(dateTimeFormatter)))
                .gte(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start.format(dateTimeFormatter))));
        List<Map> dataList = mongoTemplate.find(query, Map.class, "system_os_log");

        Map<String, Object> result = new LinkedHashMap<>();
        //遍历取值加工
        for (Map map : dataList) {
            Date date = (Date) map.get("createTime");
            timeList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            physicalFree.add(map.get("physicalFree"));
            physicalTotal.add(map.get("physicalTotal"));
            physicalUse.add(map.get("physicalUse"));
        }
        result.put("createTime", timeList);
        result.put("physicalFree", physicalFree);
        result.put("physicalTotal", physicalTotal);
        result.put("physicalUse", physicalUse);
        return data(result);
    }


}
