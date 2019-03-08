package com.newegg.ec.tool.service;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.entity.RequestMethod;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.impl.RuleService;
import com.newegg.ec.tool.utils.JsonUtils;
import com.newegg.ec.tool.utils.RegexNum;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2019/3/8
 */
public abstract class CollectionDataAbstract implements CollectDataInterface {

    protected static final Logger logger = LoggerFactory.getLogger(CollectionDataAbstract.class);

    @Autowired
    private IUrlService urlService;

    @Autowired
    private RuleService ruleService;

    @Override
    public List<Map<String, List<BigDecimal>>> collectData(String urlId) {
        List<Map<String, List<BigDecimal>>> result = new LinkedList<>();
        if (StringUtils.isBlank(urlId)) {
            return result;
        }
        ServiceUrl serviceUrl = urlService.getServiceUrlById(urlId);
        // TODO: 校验 serviceUrl 对象中的各种参数
        boolean verifyStatus = urlService.checkRequestParam(serviceUrl);
        if (!verifyStatus) {
            return result;
        }
        // 特殊 http 请求处理
        ServiceUrl newServiceUrl = reprocessingRequest(serviceUrl);

        String requestType = newServiceUrl.getRequestType();
        String urlContent = newServiceUrl.getUrlContent();
        String response = null;
        try {
            if (Objects.equals(requestType, RequestMethod.GET.toString())) {
                String params = newServiceUrl.getParamContent();
                if (StringUtils.isNotBlank(params)) {
                    urlContent += "?";
                    urlContent += params;
                }
                response = HttpClientUtil.getGetResponse(urlContent);

            } else if (Objects.equals(requestType, RequestMethod.POST.toString())) {
                String bodyContent = newServiceUrl.getBodyContent();
                response = HttpClientUtil.getPostResponse(urlContent, JSONObject.parseObject(bodyContent));

            } else {
                return result;
            }
            List<Rule> ruleList = ruleService.getRuleList(urlId);
            return processResult(response, ruleList);
        } catch (Exception e) {
            logger.error("collect data error, serviceUrl=" + newServiceUrl, e);
        }
        return result;
    }

    /**
     * 处理请求结果，如果报错，则记 log ，返回空list
     *
     * @param response
     * @return
     */
    public List<Map<String, List<BigDecimal>>> processResult(String response, List<Rule> ruleList) {
        List<Map<String, List<BigDecimal>>> result = new LinkedList<>();
        if (StringUtils.isBlank(response) || ruleList == null || ruleList.size() == 0) {
            return result;
        }
        try {
            JSONObject responseJson = JSONObject.parseObject(response);
            for (Rule rule : ruleList) {
                String formula = rule.getFormula();
                List<String> formulaKeyList = RegexNum.getFormulaKeyList(formula);
                Map<String, List<BigDecimal>> ruleDataMap = new HashMap<>();
                if (formulaKeyList.size() == 1) {
                    String formulaKey = formulaKeyList.get(0);
                    List<BigDecimal> valueList = JsonUtils.getValue(responseJson, formulaKey);
                    if (valueList.size() > 0) {
                        ruleDataMap.put(formulaKey, valueList);
                    }
                } else if (formulaKeyList.size() > 1) {
                    for (String formulaKey : formulaKeyList) {
                        List<BigDecimal> valueList = JsonUtils.getValue(responseJson, formulaKey);
                        if (valueList.size() > 0) {
                            ruleDataMap.put(formulaKey, valueList);
                        }
                    }
                }
                if (ruleDataMap.size() > 0) {
                    result.add(ruleDataMap);
                }
            }
        } catch (Exception e) {
            logger.error("process result error.", e);
        }
        return result;
    }

    /**
     * 特殊处理 url 各项参数， 如果无处理，请直接 return serviceUrl;
     *
     * @param serviceUrl
     * @return
     */
    public abstract ServiceUrl reprocessingRequest(ServiceUrl serviceUrl);
}
