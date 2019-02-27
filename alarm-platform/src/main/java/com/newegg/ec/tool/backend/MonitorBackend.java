package com.newegg.ec.tool.backend;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.dao.RuleDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private RuleDao ruleDao;

    @Scheduled(cron = "${backend.monitor}", initialDelay = 1000)
    public void executeCheckRule() {

    }

}
