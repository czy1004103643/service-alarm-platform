package com.newegg.ec.tool.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultHttpClient;
import com.newegg.ec.tool.service.IDataService;
import com.newegg.ec.tool.utils.RegexNum;
import net.minidev.json.JSONArray;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 处理类型为param的service
 * @author: gz75
 * @create: 2019-03-02 17:07
 **/
public class ParamService implements IDataService {
    @Autowired
    ServiceUrlDao serviceUrlDao;
    @Autowired
    RuleDao ruleDao;
    @Autowired
    DefaultHttpClient defaultRocketChatClient;

    @Override
    public  ArrayList dealByUrl(String id) {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(id);

        List<Rule> rule = ruleDao.selectRulesByUrlId(id);
        ArrayList<Map<String, Object>> list = new ArrayList<>();

            Response response = defaultRocketChatClient.getMessage(serviceUrl.getUrlContent());
            try {
                String body = response.body().string();
                for(Rule rule1:rule){
                    String formula = RegexNum.getFormula(rule1.getFormula());
                    Map<String, Object> map = new HashMap<>();
                    JSONArray valueArray = JsonPath.read(body, formula);
                    map.put(RegexNum.getFormulaKey(rule1.getFormula()), valueArray);
                    list.add(map);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        return list;
    }
}
