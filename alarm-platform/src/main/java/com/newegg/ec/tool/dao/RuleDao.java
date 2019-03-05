package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.Rule;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public interface RuleDao {

    List<Rule> selectRulesByUrlId(String urlId);

    Rule selectRuleById(String ruleId);

    int addRule(Rule rule);

    int updateRule(Rule rule);

    int deleteRuleByRuleId(String ruleId);

    int deleteRulesByUrlId(String urlId);

}
