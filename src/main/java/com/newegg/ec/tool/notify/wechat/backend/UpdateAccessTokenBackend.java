package com.newegg.ec.tool.notify.wechat.backend;

import com.newegg.ec.tool.notify.wechat.api.WechatBaseAPI;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.notify.wechat.entity.WechatAppInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Component
public class UpdateAccessTokenBackend implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateAccessTokenBackend.class);

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    @Autowired
    private WechatBaseAPI wechatBaseAPI;

    private static boolean checkAccessToken = true;

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String corpId = wechatBaseInfoConfig.getCorpId();
        List<WechatAppInfo> appList = wechatBaseInfoConfig.getAppList();
        Map<String, WechatAppInfo> wechatAppInfoMap = wechatBaseInfoConfig.getWechatAppInfoMap();
        updateAccessToken(corpId, appList, wechatAppInfoMap);
        //初始化后台线程
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            logger.info("wechat corpId: " + corpId);
            logger.info("wecaht app list: " + appList);
            logger.info("wechat wechatAppInfoMap: " + wechatAppInfoMap);
            updateAccessToken(corpId, appList, wechatAppInfoMap);
        }, 1, 10, TimeUnit.MINUTES);
    }

    private void updateAccessToken(String corpId, List<WechatAppInfo> appList, Map<String, WechatAppInfo> wechatAppInfoMap) {
        appList.forEach(wechatAppInfo -> {
            String users = wechatAppInfo.getUsers();
            if (StringUtils.isBlank(users)) {
                wechatAppInfo.setUsers("@all");
            }
            String secret = wechatAppInfo.getCorpSecret();
            try {
                String accessToken = wechatBaseAPI.getAccessToken(corpId, secret);
                if (StringUtils.isNotBlank(accessToken)) {
                    wechatAppInfo.setAccessToken(accessToken);
                    wechatAppInfoMap.put(wechatAppInfo.getAppName(), wechatAppInfo);
                }
            } catch (IOException e) {
                logger.error("http get wechat access token error. serect: " + secret, e);
            }
        });
        wechatBaseInfoConfig.setWechatAppInfoMap(wechatAppInfoMap);
    }
}
