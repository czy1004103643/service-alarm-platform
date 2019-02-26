package com.newegg.ec.tool.wechat.config;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.wechat.entity.WechatAppInfo;
import com.newegg.ec.utils.httpclient.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化基础资源 & 维护一个线程去定时更新所有的token
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
@Component
public class WechatBaseInfoConfig implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 企业ID
     */
    private String corpId = "";

    /**
     * Map<String, Pair<String, String>>
     *     appName      corpId  accessToken
     */
    private static final Map<String, WechatAppInfo> accessTokenMap = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initConf();
    }


    private void initConf() {
        // TODO: 加载配置文件 & 初始化 accessTokenMap & 初始化后台线程
        String secret = "";
        String urlForAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + secret;
        WechatAppInfo info = new WechatAppInfo(secret);
        try {
            String response = HttpClientUtil.getGetResponse(urlForAccessToken);
            if (StringUtils.isNotBlank(response)) {
                String accessToken = JSONObject.parseObject(response).getString("access_token");
                if (StringUtils.isNotBlank(accessToken)) {
                    info.setAccessToken(accessToken);
                    info.setAgentId(1000003);
                    accessTokenMap.put("ItemService", info);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCorpId() {
        return corpId;
    }

    public Map<String, WechatAppInfo> getAppInfo() {
        return accessTokenMap;
    }

}
