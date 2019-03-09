package com.newegg.ec.tool.backend;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.entity.MonitorData;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.service.ICollectData;
import com.newegg.ec.tool.service.IGroupService;
import com.newegg.ec.tool.service.INotifyService;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.service.impl.AppService;
import com.newegg.ec.tool.service.impl.MonitorDataService;
import com.newegg.ec.tool.service.impl.OldApiGatewayService;
import com.newegg.ec.tool.service.impl.UrlService;
import com.newegg.ec.tool.utils.MathExpressionCalculateUtil;
import com.newegg.ec.tool.utils.RegexNum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2019/2/27
 */
@Component
public class MonitorBackend {

    private static final Logger logger = LoggerFactory.getLogger(MonitorBackend.class);

    static int processors = Runtime.getRuntime().availableProcessors();

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            processors, processors * 2, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("MonitorBackend pool-thread-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    private MonitorDataService monitorDataService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private AppService appService;

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private UrlService urlService;

    @Autowired
    private OldApiGatewayService oldApiGatewayService;

    @Autowired
    private INotifyService notifyClientService;

    @Autowired
    private ICollectData apiGateWayService;

    @Autowired
    private ICollectData commonCollectDataService;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    private static final String API_GATEWAY_PREFIX_1 = "10.1.54.179";

    private static final String K_NEWEGG_ORG = "k.newegg.org";

    @Scheduled(cron = "${backend.monitor}")
    public void executeCheckRule() {
        logger.info("============== backend monitor start ==============");
        List<ServiceUrl> serviceUrlList = urlService.getServiceUrlList();
        for (ServiceUrl url : serviceUrlList) {
            String urlId = url.getUrlId();
            String urlContent = url.getUrlContent();
            Map<String, List<BigDecimal>> ruleDataMap;
            if (urlContent.contains(API_GATEWAY_PREFIX_1)) {
                ruleDataMap = apiGateWayService.collectData(urlId);
            } else {
                ruleDataMap = commonCollectDataService.collectData(urlId);
            }

            List<Rule> ruleList = ruleService.getRuleList(urlId);
            if (ruleDataMap == null || ruleDataMap.isEmpty() || ruleList == null || ruleList.isEmpty()) {
                continue;
            }
            for (Rule rule : ruleList) {
                String formula = rule.getFormula();
                String formulaKey = RegexNum.getFormulaKey(formula);
                // TODO: 仅支持单个表达式, 修改表达式正则，修改 rule.formula 唯一
                List<BigDecimal> ruleDataList = ruleDataMap.get(formula);
                if (ruleDataList != null && ruleDataList.size() > 0) {
                    for (BigDecimal ruleData : ruleDataList) {
                        Map<String, Object> dataMap = new HashMap<>(1);
                        dataMap.put(formulaKey, ruleData);
                        processRuleAndData(formula, dataMap, rule, url);
                    }
                }
            }
        }
    }

    private void processRuleAndData(String formula, Map<String, Object> dataMap, Rule rule, ServiceUrl url) {
        boolean calculate = false;
        try {
            calculate = Boolean.parseBoolean(String.valueOf(MathExpressionCalculateUtil.calculate(formula, dataMap)));
        } catch (Exception e) {
            logger.error("Rule verification failed", e);
        }
        String realData = MathExpressionCalculateUtil.getRuleDataStr(formula, dataMap);
        boolean isSend = filterAlarmMessage(rule, url,realData);
        // 如果满足，则获取其service对象
        if (isSend && calculate) {
            ServiceModel serviceModel = appService.getServiceModelById(url.getServiceId());
            notifyClientService.notifyClient(serviceModel, url, rule, realData);
        }
    }

    private boolean filterAlarmMessage(Rule rule, ServiceUrl url, String realData) {
        List<MonitorData> monitorDataList = monitorDataService.existMonitorData(rule.getRuleId());
        if (monitorDataList != null && monitorDataList.size() > 0){
            // 半小时内有报警过此规则
            return false;
        } else {
            MonitorData monitorData = new MonitorData();
            monitorData.setUrlId(url.getUrlId());
            monitorData.setRuleId(rule.getRuleId());
            monitorData.setDataContent(realData);
            monitorDataService.saveMonitorData(monitorData);
            return true;
        }
    }

}
