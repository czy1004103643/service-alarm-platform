package com.newegg.ec.tool.wechat.api;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.wechat.entity.MessageContent;
import com.newegg.ec.tool.wechat.entity.WechatAppInfo;
import com.newegg.ec.tool.wechat.entity.WechatConstant;
import com.newegg.ec.tool.wechat.entity.WechatTextMessage;
import com.newegg.ec.utils.httpclient.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 策略模式
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class WechatSendMessageAPI {

    private static final String URL_PREFIX = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    public static boolean sendMessage(String appName, MessageContent messageContent) throws IOException {
        String content = messageContent.getContent();
        if (messageContent == null || StringUtils.isBlank(content)) {
            return false;
        }
        WechatAppInfo appInfo = WechatBaseInfoConfig.getAppInfo(appName);
        if (appInfo == null) {
            return false;
        }
        String accessToken = appInfo.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        int agentId = appInfo.getAgentId();
        WechatTextMessage textMessage = new WechatTextMessage();
        textMessage.setTouser("@all");
        textMessage.setText(messageContent);
        String url = URL_PREFIX + accessToken;
        String response = HttpClientUtil.getPostResponse(url, JSONObject.parseObject(JSONObject.toJSONString(textMessage)));
        return checkResponse(JSONObject.parseObject(response));
    }

    private static boolean checkResponse(JSONObject responseObj) {
        if (responseObj != null) {
            return responseObj.getInteger(WechatConstant.ERRCODE) == 0;
        }
        return false;
    }

}
