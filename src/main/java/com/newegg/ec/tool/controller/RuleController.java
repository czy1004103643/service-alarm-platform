package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.service.IUrlService;
import com.newegg.ec.tool.service.impl.AppService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
@Controller
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private IUrlService urlService;

    @Autowired
    private AppService appService;


    @RequestMapping(value = "/getRuleList", method = RequestMethod.GET)
    @ResponseBody
    public Result getUrlListByServiceId(@PathParam("urlId") String urlId) {
        List<Rule> ruleList = ruleService.getRuleList(urlId);
        return Result.successResult(ruleList);
    }

    @RequestMapping(value = "/getServiceNameByUrl", method = RequestMethod.GET)
    @ResponseBody
    public Result getServiceNameByUrl(@PathParam("urlId") String urlId) {
        ServiceUrl url = urlService.getServiceUrlById(urlId);
        Result result = Result.successResult();
        if (url != null) {
            ServiceModel serviceModel = appService.getServiceModelById(url.getServiceId());
            result.setData(serviceModel);
        }
        return result;
    }

    @RequestMapping(value = "/getRuleById", method = RequestMethod.GET)
    @ResponseBody
    public Result getRuleById(@PathParam("ruleId") String ruleId) {
        Rule rule = ruleService.getRuleById(ruleId);
        return Result.successResult(rule);
    }

    @RequestMapping(value = "/saveRule", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRule(@RequestBody Rule rule) {
        boolean status = ruleService.saveRule(rule);
        return status ? Result.successResult() : Result.failResult();
    }

    @RequestMapping(value = "/deleteRuleById", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteRuleById(String ruleId) {
        boolean status = ruleService.deleteRuleById(ruleId);
        return status ? Result.successResult() : Result.successResult();
    }

    @RequestMapping(value = "/requestUrl", method = RequestMethod.GET)
    @ResponseBody
    public Result requestUrl(@PathParam("urlId") String urlId) {
        ServiceUrl url = urlService.getServiceUrlById(urlId);
        Pair<Boolean, Object> statusAndResponse = urlService.checkUrl(url);
        return Result.successResult(statusAndResponse);
    }

    @RequestMapping(value = "/checkRule", method = RequestMethod.POST)
    @ResponseBody
    public Result checkRule(@RequestBody Rule rule) {
        boolean status = ruleService.checkRule(rule);
        return status ? Result.successResult() : Result.failResult();
    }
}
