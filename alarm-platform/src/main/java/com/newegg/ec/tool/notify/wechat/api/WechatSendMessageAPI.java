package com.newegg.ec.tool.notify.wechat.api;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.notify.wechat.entity.WechatAppInfo;
import com.newegg.ec.tool.notify.wechat.entity.WechatConstant;
import com.newegg.ec.tool.notify.wechat.entity.WechatTextMessage;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 策略模式
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
@Component
public class WechatSendMessageAPI {

    private static final String URL_PREFIX = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    public boolean sendMessage(String appName, MessageContent messageContent) throws IOException {
        String content = messageContent.getContent();
        if (messageContent == null || StringUtils.isBlank(content)) {
            return false;
        }
        Map<String, WechatAppInfo> wechatAppInfoMap = wechatBaseInfoConfig.getWechatAppInfoMap();
        if (wechatAppInfoMap == null) {
            return false;
        }
        WechatAppInfo wechatAppInfo = wechatAppInfoMap.get(appName);
        if (wechatAppInfo == null) {
            return false;
        }
        String accessToken = wechatAppInfo.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        int agentId = wechatAppInfo.getAgentId();
        WechatTextMessage textMessage = new WechatTextMessage();
        textMessage.setTouser(wechatAppInfo.getUsers());
        textMessage.setAgentid(agentId);
        textMessage.setText(messageContent);
        String url = URL_PREFIX + accessToken;
        String response = HttpClientUtil.getPostResponse(url, JSONObject.parseObject(JSONObject.toJSONString(textMessage)));
        return checkResponse(JSONObject.parseObject(response));
    }

    private boolean checkResponse(JSONObject responseObj) {
        if (responseObj != null) {
            return responseObj.getInteger(WechatConstant.ERRCODE) == 0;
        }
        return false;
    }

}
