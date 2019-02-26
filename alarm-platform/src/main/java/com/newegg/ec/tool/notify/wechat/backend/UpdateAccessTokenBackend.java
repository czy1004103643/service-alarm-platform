package com.newegg.ec.tool.notify.wechat.backend;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.notify.wechat.entity.WechatAppInfo;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String corpId = wechatBaseInfoConfig.getCorpId();
        List<WechatAppInfo> appList = wechatBaseInfoConfig.getAppList();
        Map<String, WechatAppInfo> wechatAppInfoMap = wechatBaseInfoConfig.getWechatAppInfoMap();
        //初始化后台线程
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Map<String, WechatAppInfo> wechatAppInfoMapAfterUpdate = updateAccessToken(corpId, appList, wechatAppInfoMap);
            wechatBaseInfoConfig.setWechatAppInfoMap(wechatAppInfoMapAfterUpdate);
        }, 0, 1, TimeUnit.HOURS);
    }

    private Map<String, WechatAppInfo> updateAccessToken(String corpId, List<WechatAppInfo> appList, Map<String, WechatAppInfo> wechatAppInfoMap) {
        appList.forEach(wechatAppInfo -> {
            String users = wechatAppInfo.getUsers();
            if (StringUtils.isBlank(users)) {
                wechatAppInfo.setUsers("@all");
            }
            String secret = wechatAppInfo.getCorpSecret();
            String urlForAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + secret;
            try {
                String response = HttpClientUtil.getGetResponse(urlForAccessToken);
                if (StringUtils.isNotBlank(response)) {
                    String accessToken = JSONObject.parseObject(response).getString("access_token");
                    if (StringUtils.isNotBlank(accessToken)) {
                        wechatAppInfo.setAccessToken(accessToken);
                        wechatAppInfoMap.put(wechatAppInfo.getAppName(), wechatAppInfo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return wechatAppInfoMap;
    }

}
