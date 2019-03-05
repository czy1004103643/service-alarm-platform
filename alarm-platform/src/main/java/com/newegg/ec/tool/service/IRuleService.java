package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceUrl;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/5
 */
public interface IRuleService {

    List<Rule> getRuleList(String urlId);

    boolean saveRule(Rule rule);

    Rule getRuleById(String ruleId);

    boolean deleteRuleById(String ruleId);
}
