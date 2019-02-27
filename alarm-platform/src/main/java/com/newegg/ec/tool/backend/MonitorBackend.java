package com.newegg.ec.tool.backend;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.dao.ServiceDao;
import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.Service;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.utils.MathExpressionCalculateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        serviceUrlList.add(url2);
    }

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    @Scheduled(cron = "${backend.monitor}")
    public void executeCheckRule() {
        logger.info("============== backend monitor start ==============");
        for (ServiceUrl url : serviceUrlList) {
            String urlId = url.getUrlId();
            // 获取 url 返回的监控数据
            Map<String, Object> realDataMap = getRealDataMap(urlId);
            List<Rule> ruleList = ruleDao.selectRulesByUrlId(urlId);
            Rule temp = new Rule();
            temp.setRuleId("r001");
            temp.setUrlId("u001");
            temp.setFormula("@{response.count}>100");
            temp.setRuleAlias("请求量阈值");
            temp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            ruleList.add(temp);
            for (Rule rule : ruleList) {
                // 判断规则是否成立
                String formula = rule.getFormula();
                try {
                    boolean calculate = Boolean.parseBoolean(String.valueOf(MathExpressionCalculateUtil.calculate(formula, realDataMap)));
                    // 如果满足，则获取其service对象
                    if (calculate) {
                        Service service = serviceDao.selectServiceById(url.getServiceId());
                        // 获取其通知方式
                        // String alarmRoute = service.getAlarmRoute();
                        sendMessageService(null, new MessageContent(temp.toString()), service);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean sendMessageService(String alarmRoute, MessageContent messageContent, Service service) {
        // 先判断发送方式有哪些， wechat rocketchat 等
        try {
            return wechatSendMessageAPI.sendMessage("HierarchyService", messageContent);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Map<String, Object> getRealDataMap(String urlId) {
        Map<String, Object> result = new HashMap<>();
        result.put("response.count", 500);
        result.put("time.request.time", 1000);
        return result;
    }
}
