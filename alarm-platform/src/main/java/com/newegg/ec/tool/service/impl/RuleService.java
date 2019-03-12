package com.newegg.ec.tool.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.service.IUrlService;
import com.newegg.ec.tool.utils.CommonUtils;
import com.newegg.ec.tool.utils.JsonUtils;
import com.newegg.ec.tool.utils.MathExpressionCalculateUtil;
import com.newegg.ec.tool.utils.RegexNum;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private IUrlService urlService;

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
        if (StringUtils.isBlank(ruleId)) {
            return null;
        }
        try {
            return ruleDao.selectRuleById(ruleId);
        } catch (Exception e) {
            logger.error("get rule by id error.", e);
            return null;
        }
    }

    @Override
    public boolean deleteRuleById(String ruleId) {
        if (StringUtils.isBlank(ruleId)) {
            return false;
        }
        try {
            return ruleDao.deleteRuleByRuleId(ruleId) > 0;
        } catch (Exception e) {
            logger.error("delete rule error.", e);
            return false;
        }
    }

    @Override
    public boolean checkRule(Rule rule) {
        if (rule == null || StringUtils.isBlank(rule.getUrlId()) || StringUtils.isBlank(rule.getFormula())) {
            return false;
        }
        try {
            ServiceUrl url = urlService.getServiceUrlById(rule.getUrlId());
            Pair<Boolean, Object> statusAndResponse = urlService.checkUrl(url);
            if (statusAndResponse.getKey()) {
                Object value = statusAndResponse.getValue();
                if (value != null) {
                    String formula = rule.getFormula();
                    Object read = JsonPath.read(statusAndResponse.getValue(), formula);
                    logger.info(read.toString());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("check rule error.", e);
            return false;
        }
    }
}
