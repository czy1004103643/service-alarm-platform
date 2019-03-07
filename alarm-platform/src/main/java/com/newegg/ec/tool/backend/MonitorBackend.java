package com.newegg.ec.tool.backend;

import com.alibaba.fastjson.JSONArray;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.dao.AppServiceDao;
import com.newegg.ec.tool.dao.MonitorDataDao;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.*;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.service.INotifyService;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.service.impl.ApiGatewayService;
import com.newegg.ec.tool.utils.CommonUtils;
import com.newegg.ec.tool.utils.MathExpressionCalculateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static int processors = Runtime.getRuntime().availableProcessors();

    private static ExecutorService threadPool = new ThreadPoolExecutor(
            processors, processors * 2, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("MonitorBackend pool-thread-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
    @Autowired
    private MonitorDataDao monitorDataDao;

    @Autowired
    private AppServiceDao appServiceDao;

    @Autowired
    private IRuleService ruleService;

    @Autowired
    private ServiceUrlDao serviceUrlDao;

    @Autowired
    private ApiGatewayService apiGatewayService;

    @Autowired
    private INotifyService notifyClientService;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    @Scheduled(cron = "${backend.monitor}")
    public void executeCheckRule() {
        logger.info("============== backend monitor start ==============");
        List<ServiceUrl> serviceUrlList = serviceUrlDao.selectAllUrl();
        for (ServiceUrl url : serviceUrlList) {
            String urlId = url.getUrlId();

            List<Rule> ruleList = ruleService.getRuleList(urlId);




            // key: String value:数值
            ArrayList<HashMap<String, Object>> list = apiGatewayService.dealByUrl(urlId);
            // realDataMap 可能会移除部分数据

            // key: String value: list
            for (HashMap<String, Object> realDataMap : list) {
                // TODO: map 中如果出现 1-n 或 n-n 情况，则预警数据不准
                Map<String, Object> dataMapWithArray = processDataForArray(realDataMap);
                processRuleAndData(url, ruleList, realDataMap);
                processRuleAndDataForArray(url, ruleList, dataMapWithArray);
            }

        }
    }

    private void processRuleAndDataForArray(ServiceUrl url, List<Rule> ruleList, Map<String, Object> dataMap) {
        if (dataMap.size() > 0) {
            Iterator<Map.Entry<String, Object>> iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                List<BigDecimal> dataList = JSONArray.parseArray(next.getValue().toString(), BigDecimal.class);
                String key = next.getKey();
                for (BigDecimal bigDecimal : dataList) {
                    Map<String, Object> oneDataMap = new HashMap<>();
                    oneDataMap.put(key, bigDecimal.toString());
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
                    String realData = MathExpressionCalculateUtil.getRuleDataStr(formula, dataMap);
                    // TODO: 查询最近半小时是否已经发送过此规则的报警消息 if(none) send();

                    String dataID = url.getUrlId() + rule.getRuleId();
                    MonitorData monitorData = monitorDataDao.selectDataById(dataID);

                    if (monitorData == null) {
                        MonitorData monitorData1 = new MonitorData();
                        monitorData1.setDataId(dataID);
                        monitorData1.setUrlId(url.getUrlId());
                        monitorData1.setRuleId(rule.getRuleId());
                        monitorData1.setDataContent(realData);
                        monitorData1.setUpdateTime(CommonUtils.getCurrentTimestamp());
                        monitorDataDao.addMonitorData(monitorData1);
                        ServiceModel serviceModel = appServiceDao.selectServiceById(url.getServiceId());
                        notifyClientService.notifyClient(serviceModel,  url, rule, realData);
                    } else {
                        Timestamp timestamp = monitorData.getUpdateTime();
                        Timestamp currentstamp = CommonUtils.getCurrentTimestamp();
                        boolean diffFlag = CommonUtils.getTimstapDiff(timestamp, currentstamp);
//                        if (diffFlag) {
                            ServiceModel serviceModel = appServiceDao.selectServiceById(url.getServiceId());
                            notifyClientService.notifyClient(serviceModel,  url, rule, realData);
//                        }

                    }


                }
            }
        }
    }






    private Map<String, Object> processDataForArray(Map<String, Object> realDataMap) {
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


}
