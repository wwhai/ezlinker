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

@RestController
@RequestMapping("/monitor/jvm")
public class JvmMonitorController extends XController {
    public JvmMonitorController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 当前运行参数
     *
     * @return
     */
    @GetMapping("/currentState")
    public R running() {
        return data(OSMonitor.getJvmRunningState());
    }

    /**
     * 24小时的数据
     *
     * @return
     */
    @GetMapping("/running24h")
    public R jvmRunning24h() throws ParseException {
        // 时间轴
        List<Object> timeList = new ArrayList<>();
        List<Object> vmUse = new ArrayList<>();
        List<Object> vmMax = new ArrayList<>();
        List<Object> vmFree = new ArrayList<>();
        List<Object> vmTotal = new ArrayList<>();
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
        List<Map> dataList = mongoTemplate.find(query, Map.class, "jvm_state_log");
        //遍历取值加工
        for (Map map : dataList) {
            Date date = (Date) map.get("createTime");
            timeList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            vmUse.add(map.get("jvmUse"));
            vmMax.add(map.get("jvmMax"));
            vmFree.add(map.get("jvmFree"));
            vmTotal.add(map.get("jvmTotal"));
        }

        //
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("time", timeList);
        result.put("jvmUse", vmUse);
        result.put("jvmMax", vmMax);
        result.put("jvmFree", vmFree);
        result.put("jvmTotal", vmTotal);
        return data(result);
    }

}
