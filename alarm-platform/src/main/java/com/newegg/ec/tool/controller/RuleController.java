package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.service.IRuleService;
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

    @RequestMapping(value = "/getRuleList", method = RequestMethod.GET)
    @ResponseBody
    public Result getUrlListByServiceId(@PathParam("urlId") String urlId) {
        List<Rule> ruleList = ruleService.getRuleList(urlId);
        return Result.successResult(ruleList);
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
}
