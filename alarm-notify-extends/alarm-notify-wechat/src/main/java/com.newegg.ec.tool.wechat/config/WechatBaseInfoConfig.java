package com.newegg.ec.tool.wechat.config;

import com.newegg.ec.tool.wechat.entity.WechatAppInfo;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化基础资源 & 维护一个线程去定时更新所有的token
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class WechatBaseInfoConfig {

    private WechatBaseInfoConfig() {}

    public static final String CORP_ID = "corpid";

    public static final String ACCESS_TOKEN = "access_token";

    /**
     * 企业ID
     */
    private static String corpId = "ww0f00cb4440ed3cf1";

    /**
     * Map<String, Pair<String, String>>
     *     appName      corpId  accessToken
     */
    private static Map<String, WechatAppInfo> accessTokenMap = new ConcurrentHashMap<>();

    static {
        // TODO: 加载配置文件 & 初始化 accessTokenMap & 初始化后台线程
        WechatAppInfo info = new WechatAppInfo("Gd55K7N0q2WGEMob-PJ0oz3ePsk9zqndiDVgQyQPgk0");
        info.setAccessToken("tVJjpitkGTwSMR7B-ALy-ljcZRNUJueNGwqa2hSRCDd2v3sOXq2WMPJ8bsetvek7O6OTax2zJb0U214wHyoPhrkqH655861G6icaaTSGOywVMcWXLIdqQoLtV2QpnPsDAnBkoT4jWH5jJFQTgzsG7x3UOamg0XD_NX74sYxYAfokrVu36vh7y2lUpNpPvteGpXVzNF6aVk1zGVZLpLedng");
        accessTokenMap.put("ItemService", info);
    }

    public static String getCorpId() {
        return corpId;
    }

    public static WechatAppInfo getAppInfo(String appName) {
        return accessTokenMap.get(appName);
    }

    /*public static void setAccessToken(String appName, String accessToken) {
        if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(appName)) {

        } else {
            // TODO: logger warn
        }
    }*/




}
