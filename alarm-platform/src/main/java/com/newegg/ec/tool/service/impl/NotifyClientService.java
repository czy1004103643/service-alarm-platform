package com.newegg.ec.tool.service.impl;


import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.notify.rocket.DefaultHttpClient;
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
    @Autowired
    private DefaultHttpClient defaultHttpClient;


    @Override
    public void notifyClient(ServiceModel serviceModel, ServiceUrl url, Rule rule, String realData) {
        if (serviceModel == null
                || StringUtils.isBlank(serviceModel.getAlarmWay())
               ) {
            return;
        }
        String alarmWays = serviceModel.getAlarmWay();

        List<String> alarmWayList = CommonUtils.stringToList(alarmWays);
        for (String way : alarmWayList) {
            String wechatAppName = serviceModel.getWechatAppName();
            switch (way) {
                case "WECHAT":
                    try {
                        System.err.println("================= Send Message =================");
                        MessageContent webMessageContent = CommonUtils.buildWebMessageContent(serviceModel, url, rule, realData);
                        boolean status = wechatSendMessageAPI.sendMessage(wechatAppName, webMessageContent );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ROCKETCHAT":
                    try {
                        MessageContent rocketMessage = CommonUtils.buildRocketMessageContent(serviceModel, url, rule, realData);
                        int code = defaultHttpClient.postRocketMessage(rocketMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }

        }
    }


}
