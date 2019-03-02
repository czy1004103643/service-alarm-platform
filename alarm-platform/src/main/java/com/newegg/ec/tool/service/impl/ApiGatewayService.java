package com.newegg.ec.tool.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultHttpClient;
import com.newegg.ec.tool.service.IDataService;
import net.minidev.json.JSONArray;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: deal http request data
 * @author: gz75
 * @create: 2019-02-27 17:29
 **/
@Service
public class ApiGatewayService implements IDataService {

    @Autowired
    ServiceUrlDao serviceUrlDao;
    @Autowired
    RuleDao ruleDao;
    @Autowired
    DefaultHttpClient defaultRocketChatClient;

    @Override
    public Map<String, Object> dealByUrl(String id) {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(id);

        try {

            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String timeDay = sdf2.format(new Date());
            String startTime = timeDay + " 00:00:00";
            Date startDate = sdf.parse(startTime);
            long startTimstamp = startDate.getTime();


            String endTime=timeDay+" 23:59:59";
            Date endDate = sdf.parse(endTime);
            long endTimestamp = endDate.getTime();


            DocumentContext ext = JsonPath.parse(serviceUrl.getBodyConent());
            JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
            ext.set(p, endTimestamp);
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
