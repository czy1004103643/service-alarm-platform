package com.newegg.ec.tool.service.impl;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultHttpClient;
import com.newegg.ec.tool.service.IDataService;
import com.newegg.ec.tool.utils.RegexNum;
import net.minidev.json.JSONArray;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public  ArrayList  dealByUrl(String urlId) {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(urlId);
        java.util.List<Rule> rule = ruleDao.selectRulesByUrlId(urlId);
         ArrayList<Map<String, Object>> list = new ArrayList<>();

        try {

            TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String timeDay = sdf2.format(new Date());
            String startTime = timeDay + " 00:00:00";
            Date startDate = sdf.parse(startTime);
            long startTimstamp = startDate.getTime();


            String endTime = timeDay + " 23:59:59";
            Date endDate = sdf.parse(endTime);
            long endTimestamp = endDate.getTime();


            DocumentContext ext = JsonPath.parse(serviceUrl.getBodyConent());
            JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
            ext.set(p, endTimestamp);
            JsonPath p2 = JsonPath.compile("$.query.bool.must[0].range.RequestTime.gte");
            ext.set(p2, startTimstamp);

            MessageContent messageContent = new MessageContent();
            messageContent.setContent(ext.jsonString());
            Response response = defaultRocketChatClient.postNetMessage(serviceUrl.getUrlContent(), messageContent);
            String jsonStr = response.body().string();


            for(Rule rule1:rule){
                String formula = RegexNum.getFormula(rule1.getFormula());
                JSONArray valueArray = JsonPath.read(jsonStr, formula);
                Map<String, Object> map = new HashMap<>();
                map.put(RegexNum.getFormulaKey(rule1.getFormula()), valueArray);
                list.add(map);
            }

            return list;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



}
