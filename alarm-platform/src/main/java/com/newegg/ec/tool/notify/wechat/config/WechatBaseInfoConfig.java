package com.newegg.ec.tool.notify.wechat.config;

import com.newegg.ec.tool.notify.wechat.entity.WechatAppInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 初始化基础资源 & 维护一个线程去定时更新所有的token
 *
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
@Configuration
@ConfigurationProperties(prefix = "alarm.notify.wechat", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class WechatBaseInfoConfig implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 企业ID
     */
    private String corpId;

    private List<WechatAppInfo> appList = new LinkedList<>();

    /**
     * Map<String, Pair<String, String>>
     * appName      corpId  accessToken
     */
    private Map<String, WechatAppInfo> wechatAppInfoMap = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /*if (StringUtils.isBlank(corpId)) {
            throw new NullParamException("wechat param is null!");
        }*/
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public List<WechatAppInfo> getAppList() {
        return appList;
    }

    public void setAppList(List<WechatAppInfo> appList) {
        this.appList = appList;
    }

    public Map<String, WechatAppInfo> getWechatAppInfoMap() {
        return wechatAppInfoMap;
    }

    public void setWechatAppInfoMap(Map<String, WechatAppInfo> wechatAppInfoMap) {
        this.wechatAppInfoMap = wechatAppInfoMap;
    }
}
