package com.newegg.ec.tool.service.impl;


import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.service.INotifyService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;


    @Override
    public void notifyClient(ServiceModel service, MessageContent messageContent) {
        if (service == null
                || messageContent == null
                || StringUtils.isBlank(service.getAlarmWay())
                || StringUtils.isBlank(messageContent.getContent())) {
            return;
        }
        String alarmWays = service.getAlarmWay();

        try {
            wechatSendMessageAPI.sendMessage("HierarchyService", messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
