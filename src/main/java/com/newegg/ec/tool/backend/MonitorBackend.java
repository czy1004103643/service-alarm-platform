package com.newegg.ec.tool.backend;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.entity.MonitorData;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.ICollectData;
import com.newegg.ec.tool.service.IGroupService;
import com.newegg.ec.tool.service.INotifyService;
import com.newegg.ec.tool.service.IRuleService;
import com.newegg.ec.tool.service.impl.AppService;
import com.newegg.ec.tool.service.impl.MonitorDataService;
import com.newegg.ec.tool.service.impl.UrlService;
import com.newegg.ec.tool.utils.RegexNum;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private INotifyService notifyClientService;

    @Autowired
    private ICollectData commonCollectDataService;

    @Scheduled(cron = "${backend.monitor}")
    public void executeCheckRule() {
        List<ServiceUrl> serviceUrlList = urlService.getServiceUrlList();
        for (ServiceUrl url : serviceUrlList) {
            String urlId = url.getUrlId();
            String urlContent = url.getUrlContent();
            Map<Rule, JSONArray> ruleDataMap = commonCollectDataService.collectData(urlId);

            if (ruleDataMap.size() > 0) {
                processRuleAndData(ruleDataMap, url);
            }
        }
    }

    private void processRuleAndData(Map<Rule, JSONArray> map, ServiceUrl url) {
        Iterator<Map.Entry<Rule, JSONArray>> iterable = map.entrySet().iterator();
        Map.Entry<Rule, JSONArray> entry = iterable.next();
        Rule rule = entry.getKey();
        JSONArray array = entry.getValue();
        LinkedHashMap firElem = (LinkedHashMap) array.get(0);
        String realKey = RegexNum.getRealKey(rule.getFormula());
        if (realKey != null) {
            String realData = realKey + "=" + firElem.get(realKey);
            boolean isSend = filterAlarmMessage(rule, url, realData);
            if (isSend) {
                ServiceModel serviceModel = appService.getServiceModelById(url.getServiceId());
                notifyClientService.notifyClient(serviceModel, url, rule, realData);
            }
        }
    }

    private boolean filterAlarmMessage(Rule rule, ServiceUrl url, String realData) {

        String dataid = rule.getRuleId() + url.getUrlId();

        List<MonitorData> dataList = monitorDataService.existData(String.valueOf(dataid.hashCode()));

        if (dataList.size() == 0) {
            MonitorData monitorData = new MonitorData();
            monitorData.setUrlId(url.getUrlId());
            monitorData.setRuleId(rule.getRuleId());
            monitorData.setDataContent(realData);
            monitorDataService.saveMonitorData(monitorData);
            return true;
        } else {
            List<MonitorData> monitorDataList = monitorDataService.existMonitorData(String.valueOf(dataid.hashCode()));
            if (monitorDataList != null && monitorDataList.size() > 0) {
                // 半小时内有报警过此规则
                monitorDataService.updataMonitorData(monitorDataList.get(0));
                return true;
            } else {
                return false;
            }
        }
    }

}
