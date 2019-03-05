package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/5
 */
@Service
public class RuleService implements IRuleService {

    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleDao ruleDao;

    @Override
    public List<Rule> getRuleList(String urlId) {
        if (StringUtils.isBlank(urlId)) {
            return null;
        }
        try {
            return ruleDao.selectRulesByUrlId(urlId);
        } catch (Exception e) {
            logger.error("get rule list error.", e);
            return null;
        }
    }

    @Override
    public boolean saveRule(Rule rule) {
        if (rule == null) {
            return false;
        }
        String urlId = rule.getUrlId();
        if (StringUtils.isBlank(urlId)) {
            return false;
        }
        try {
            // TODO: check formula
            String ruleId = rule.getRuleId();
            rule.setUpdateTime(CommonUtils.getCurrentTimestamp());
            if (StringUtils.isBlank(ruleId)) {
                rule.setRuleId(CommonUtils.getUUID());
                return ruleDao.addRule(rule) > 0;
            }
            return ruleDao.updateRule(rule) > 0;
        } catch (Exception e) {
            logger.error("save rule error.", e);
            return false;
        }
    }

    @Override
    public Rule getRuleById(String ruleId) {
        return null;
    }

    @Override
    public boolean deleteRuleById(String ruleId) {
        return false;
    }
}
