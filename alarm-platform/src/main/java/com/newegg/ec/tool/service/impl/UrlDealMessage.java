package com.newegg.ec.tool.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultRocketChatClient;
import com.newegg.ec.tool.service.DealMesage;
import net.minidev.json.JSONArray;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

/**
 * @description: deal http request data
 * @author: gz75
 * @create: 2019-02-27 17:29
 **/
@Service
public class UrlDealMessage implements DealMesage {
    @Autowired
    ServiceUrlDao serviceUrlDao;
    @Autowired
    RuleDao ruleDao;
    @Autowired
    DefaultRocketChatClient defaultRocketChatClient;

    @Override
    public Map<String, Object> dealByUrl(String id) {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(id);

        try {
            TimeZone tz = TimeZone.getTimeZone("America/New_York");
            Calendar calendar = Calendar.getInstance(tz, Locale.US);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
            Calendar calendar2 = Calendar.getInstance(tz, Locale.US);

            //当前时间的前一个小时
            long startTime = calendar.getTimeInMillis();
            long endTime = calendar2.getTimeInMillis();
            DocumentContext ext = JsonPath.parse(serviceUrl.getBody());
            JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
            ext.set(p, startTime);
            JsonPath p2 = JsonPath.compile("$.query.bool.must[0].range.RequestTime.gte");
            ext.set(p2, endTime);
            Response response = defaultRocketChatClient.postMessage(serviceUrl.getUrlContent(), "8AkGjraxsWF7spNnu", ext.jsonString());
            String jsonStr = response.body().string();
            JSONArray valueArray = JsonPath.read(jsonStr, "aggregations.result.buckets");
            Map<String, Object> map = new HashMap<>();
            map.put("aggregations.result.buckets", valueArray);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
