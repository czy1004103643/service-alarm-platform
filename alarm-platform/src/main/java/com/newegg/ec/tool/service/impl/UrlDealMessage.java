package com.newegg.ec.tool.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultRocketChatClient;
import com.newegg.ec.tool.service.DealMessage;
import net.minidev.json.JSONArray;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: deal http request data
 * @author: gz75
 * @create: 2019-02-27 17:29
 **/
@Service
public class UrlDealMessage implements DealMessage {
    @Autowired
    ServiceUrlDao serviceUrlDao;
    @Autowired
    RuleDao ruleDao;
    @Autowired
    DefaultRocketChatClient defaultRocketChatClient;


    @Override
    public Map<String, Object> dealByUrl(String id) {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(id);
        serviceUrl = new ServiceUrl("u001", "s001",
                "http://10.1.54.179:8900/e4/api-logs/_search",
                "POST",
                "param",
                body,
                "大吉大利，今晚吃鸡",
                new Timestamp(System.currentTimeMillis()));

        try {
//           TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
//            Calendar cal2 = Calendar.getInstance();
//            Calendar cal1 = Calendar.getInstance();
//            cal1.set(Calendar.HOUR_OF_DAY -1, cal1.get(Calendar.HOUR_OF_DAY) - 24);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String timeDay = sdf2.format(new Date());
            String startTime = timeDay + " 16:00:00";
            Date startDate = sdf.parse(startTime);
            long startTimstamp = startDate.getTime();




            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            Date endDate=calendar.getTime();
            String endDay = sdf2.format(endDate);
            String endTime=endDay+" 15:59:59";
            Date endDate2 = sdf.parse(endTime);
            long endTimestatp = endDate2.getTime();









            //当前时间的前一个小时
//            long startTime = cal1.getTimeInMillis();
//            long endTime = cal2.getTimeInMillis();
            DocumentContext ext = JsonPath.parse(serviceUrl.getBody());
            JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
            ext.set(p, endTimestatp);
            JsonPath p2 = JsonPath.compile("$.query.bool.must[0].range.RequestTime.gte");
            ext.set(p2, startTimstamp);

            Response response = defaultRocketChatClient.postNetMessage(serviceUrl.getUrlContent(), ext.jsonString());
            String jsonStr = response.body().string();
            System.out.println(jsonStr);
            JSONArray valueArray = JsonPath.read(jsonStr, "aggregations.result.buckets");
            System.out.println(valueArray);
            Map<String, Object> map = new HashMap<>();
            map.put("aggregations.result.buckets.doc_count", valueArray);
            System.err.println(valueArray);
            return map;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final String body = "{ \n" +
            "    \"size\": 0, \n" +
            "    \"query\": { \n" +
            "        \"bool\": { \n" +
            "            \"must\": [ \n" +
            "                { \n" +
            "                    \"range\": { \n" +
            "                        \"RequestTime\": { \n" +
            "                            \"lte\": 1550908799999, \n" +
            "                            \"gte\": 1550822400000 \n" +
            "                        } \n" +
            "                    } \n" +
            "                }, \n" +
            "                { \n" +
            "                    \"query_string\": { \n" +
            "                        \"query\": \"ApiId:364e9234-254b-4d0e-a9bd-02c43960557f AND GatewayType:P AND SpendTime:[ 1000 TO * ]\" \n" +
            "\n" +
            "                    } \n" +
            "                } \n" +
            "            ] \n" +
            "        } \n" +
            "    }, \n" +
            "    \"aggregations\": { \n" +
            "        \"result\": { \n" +
            "            \"terms\": { \n" +
            "                \"field\": \"Timestamp\", \n" +
            "                \"size\": 2147483647, \n" +
            "                \"order\": { \n" +
            "                    \"_term\": \"asc\" \n" +
            "                } \n" +
            "            } \n" +
            "        } \n" +
            "    } \n" +
            "}";
}
