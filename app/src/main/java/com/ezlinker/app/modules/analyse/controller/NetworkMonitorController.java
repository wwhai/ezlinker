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
@RequestMapping("/monitor/network")
public class NetworkMonitorController extends XController {
    @Resource
    MongoTemplate mongoTemplate;

    public NetworkMonitorController(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    private static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static boolean isMacOS() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac") && os.indexOf("os") > 0 && !os.contains("x");
    }


    @GetMapping("/currentState")
    public R currentNetworkState() {
        if (isLinux() || isMacOS()) {
            return data(OSMonitor.getNetworkState());
        }
        return data(0);
    }

    /**
     * 网卡24小时数据
     *
     * @return
     */
    @GetMapping("/running24h")
    public R networkRunning24h() throws ParseException {
        List<Object> timeList = new ArrayList<>();
        List<Object> netWorkIn = new ArrayList<>();
        List<Object> netWorkOut = new ArrayList<>();

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
        List<Map> dataList = mongoTemplate.find(query, Map.class, "system_network_log");

        Map<String, Object> result = new LinkedHashMap<>();
        //遍历取值加工
        for (Map map : dataList) {
            Date date = (Date) map.get("createTime");
            timeList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            netWorkIn.add(map.get("netWorkIn"));
            netWorkOut.add(map.get("netWorkOut"));
        }

        result.put("createTime", timeList);
        result.put("netWorkIn", netWorkIn);
        result.put("netWorkOut", netWorkOut);

        return data(result);

    }
}
