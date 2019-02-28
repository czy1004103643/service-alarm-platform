package com.newegg.ec.tool.backend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceDao;
import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.service.INotifyService;
import com.newegg.ec.tool.service.impl.UrlDealMessage;
import com.newegg.ec.tool.utils.MathExpressionCalculateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jay.H.Zou
 * @date 2019/2/27
 */
@Component
public class MonitorBackend{

    private static final Logger logger = LoggerFactory.getLogger(MonitorBackend.class);

    static int processors = Runtime.getRuntime().availableProcessors();

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            processors, processors * 2, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("MonitorBackend pool-thread-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    static List<ServiceUrl> serviceUrlList = new ArrayList<>(2);

    static {

        ServiceUrl url1 = new ServiceUrl("u001", "s001", "https://www.github.com/", "GET", "param", "body", "test", new Timestamp(System.currentTimeMillis()));
        ServiceUrl url2 = new ServiceUrl("u002", "s001", "https://www.github.com/zouhuajian", "GET", "param", "body", "test", new Timestamp(System.currentTimeMillis()));
        serviceUrlList.add(url1);
        //serviceUrlList.add(url2);
    }

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private UrlDealMessage urlDealMessage;

    @Autowired
    private INotifyService notifyClientService;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    @Scheduled(cron = "${backend.monitor}")
    public void executeCheckRule() {
        logger.info("============== backend monitor start ==============");
        for (ServiceUrl url : serviceUrlList) {
            String urlId = url.getUrlId();

            List<Rule> ruleList = ruleDao.selectRulesByUrlId(urlId);

            // TODO: delete
            Rule tempRule = new Rule();
            tempRule.setRuleId("r001");
            tempRule.setUrlId("u001");
            tempRule.setFormula("@{aggregations.result.buckets.doc_count}>100");
            tempRule.setRuleAlias("请求量阈值");
            tempRule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            ruleList.add(tempRule);
            // TODO: delete

            if (ruleList == null || ruleList.isEmpty()) {
                continue;
            }
            // 获取 url 返回的监控数据
            Map<String, Object> realDataMap = urlDealMessage.dealByUrl(urlId);
            Map<String, Object> preprocessDataForArray = processDataForArray(realDataMap);

            processRuleAndData(url, ruleList, realDataMap);
            processRuleAndDataForArray(url, ruleList, preprocessDataForArray);
        }
    }

    private void processRuleAndDataForArray(ServiceUrl url, List<Rule> ruleList, Map<String, Object> dataMap) {
        if (dataMap.size() > 0) {
            Iterator<Map.Entry<String, Object>> iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                JSONArray jsonArray = JSONArray.parseArray(next.getValue().toString());
                String key = next.getKey();
                String[] split = key.split(".");
                String monitorKey = split[split.length - 1];
                for (int index = 0; index < jsonArray.size(); index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    Object realValue = jsonObject.get(monitorKey);
                    Map<String, Object> oneDataMap = new HashMap<>();
                    oneDataMap.put(key, realValue);
                    processRuleAndData(url, ruleList, oneDataMap);
                }
            }
        }
    }

    private void processRuleAndData(ServiceUrl url, List<Rule> ruleList, Map<String, Object> dataMap) {
        if (dataMap != null && dataMap.size() > 0) {
            for (Rule rule : ruleList) {
                // 判断规则是否成立
                boolean calculate = false;
                String formula = rule.getFormula();
                if (StringUtils.isNotBlank(formula)) {
                    try {
                        calculate = Boolean.parseBoolean(String.valueOf(MathExpressionCalculateUtil.calculate(formula, dataMap)));
                    } catch (Exception e) {
                        logger.error("匹配规则出错", e);
                    }
                }
                // 如果满足，则获取其service对象
                if (calculate) {
                    ServiceModel serviceModel = serviceDao.selectServiceById(url.getServiceId());
                    serviceModel = new ServiceModel();
                    serviceModel.setAlarmWay("WECHAT");
                    serviceModel.setWechatAppName("HierarchyService");
                    // 获取其通知方式
                    // String alarmRoute = serviceModel.getAlarmRoute();
                    //sendMessageService(null, new MessageContent(temp.toString()), serviceModel);

                    notifyClientService.notifyClient(serviceModel, new MessageContent("假数据========="));
                }
            }
        }
    }


    private Map<String, Object> processDataForArray(Map<String, Object> realDataMap){
        Iterator<Map.Entry<String, Object>> iterator = realDataMap.entrySet().iterator();
        Map<String, Object> arrayDataMap = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            Object value = next.getValue();
            if (value == null) {
                iterator.remove();
                continue;
            }
            if (value instanceof List) {
                arrayDataMap.put(next.getKey(), value);
                iterator.remove();
            }
        }
        return arrayDataMap;
    }

    /*private Map<String, Object> getRealDataMap(String urlId) {
        Map<String, Object> result = new HashMap<>();
        result.put("response.count", 500);
        result.put("time.request.time", 1000);
        return result;
    }*/
}
