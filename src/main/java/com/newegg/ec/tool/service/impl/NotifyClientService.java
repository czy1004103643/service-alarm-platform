package com.newegg.ec.tool.service.impl;


import com.newegg.ec.tool.entity.*;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.service.INotifyService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/28
 */
@Service
public class NotifyClientService implements INotifyService {

    private static final Logger logger = LoggerFactory.getLogger(NotifyClientService.class);

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;


    @Override
    public void notifyClient(ServiceModel serviceModel, ServiceUrl url, Rule rule, String realData) {
        if (serviceModel == null || StringUtils.isBlank(serviceModel.getAlarmWay())) {
            return;
        }
        String alarmWays = serviceModel.getAlarmWay();

        List<String> alarmWayList = CommonUtils.stringToList(alarmWays);
        for (String way : alarmWayList) {
            String wechatAppName = serviceModel.getWechatAppName();
            switch (way) {
                case "WECHAT":
                    try {
                        logger.info("*************************** Send Message ***************************");
                        // MessageContent messageContent = buildTextMessageContent(serviceModel, url, rule, realData);
                        MessageContent messageContent = buildTextCardMessage(serviceModel, url, rule, realData);
                        if (StringUtils.isNotBlank(wechatAppName)) {
                            List<String> apppNameList = CommonUtils.stringToList(wechatAppName);
                            for (String appName : apppNameList) {
                                if (StringUtils.isNotBlank(appName)) {
                                    boolean status = wechatSendMessageAPI.sendCardMessage(appName, messageContent);
                                    if (!status) {
                                        logger.warn("send wechat message failed, appName=" + appName + ", messageContent=" + messageContent);
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        logger.error("send wechat error", e);
                    }
                    break;
                case "ROCKETCHAT":
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 格式化输出rocket消息格式
     *
     * @param serviceModel
     * @param serviceUrl
     * @param rule
     * @param realData
     * @return
     */
    private static MessageContent buildRocketMessageContent(ServiceModel serviceModel, ServiceUrl serviceUrl, Rule rule, String realData) {
        return buildMessageContent(serviceModel, serviceUrl, rule, realData, "\\n");
    }

    /**
     * 格式化输出wechat消息格式
     */
    private static MessageContent buildTextMessageContent(ServiceModel serviceModel, ServiceUrl serviceUrl, Rule rule, String realData) {
        return buildMessageContent(serviceModel, serviceUrl, rule, realData, "\n");
    }

    private static MessageContent buildMessageContent(ServiceModel serviceModel, ServiceUrl serviceUrl, Rule rule, String realData, String wrap) {
        MessageContent messageContent = new MessageContent();
        messageContent.setTitle(serviceModel.getServiceName());
        StringBuffer buffer = new StringBuffer();

        buffer.append("Group: ").append(serviceModel.getGroupName()).append(wrap)
                .append("Service: ").append(serviceModel.getServiceName()).append(wrap)
                .append("URL Desc: ").append(serviceUrl.getDescription()).append(wrap)
                .append("Rule: ").append(rule.getRuleAlias()).append(wrap)
                .append("Formula: ").append(rule.getFormula()).append(wrap)
                .append("Monitor Data: ").append(realData).append(wrap)
                .append("Rule Desc: ").append(rule.getDescription()).append(wrap)
                .append("Time: ").append(CommonUtils.formatTime(System.currentTimeMillis()));
        messageContent.setContent(buffer.toString());
        return messageContent;
    }

    private static MessageContent buildTextCardMessage(ServiceModel serviceModel, ServiceUrl serviceUrl, Rule rule, String realData) {
        MessageContent messageContent = new MessageContent();
        messageContent.setTitle(serviceModel.getGroupName() + " " + serviceModel.getServiceName() + " \n" + serviceUrl.getDescription());
        messageContent.setUrl("https://www.newegg.com/");
        messageContent.setBtntxt("没有更多了");
        StringBuffer description = new StringBuffer();
        description.append("<div class=\"gray\">")
                .append(CommonUtils.formatTime(System.currentTimeMillis()))
                .append("</div>")
                .append(rule.getDescription())
                .append(": <div class=\"highlight\">")
                .append(realData)
                .append("</div>")
                .append(rule.getRuleAlias())
                .append(": <div class=\"highlight\">")
                .append(rule.getFormula())
                .append("</div>");
        messageContent.setDescription(description.toString());
        System.err.println(description);
        return messageContent;
    }
}
