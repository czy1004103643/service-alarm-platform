package com.newegg.ec.tool.service.impl;


import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.ServiceModel;
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
    public void notifyClient(ServiceModel service, MessageContent messageContent) {
        if (service == null
                || messageContent == null
                || StringUtils.isBlank(service.getAlarmWay())
                || StringUtils.isBlank(messageContent.getContent())) {
            return;
        }
        String alarmWays = service.getAlarmWay();
        List<String> alarmWayList = CommonUtils.stringToList(alarmWays);
        for (String way : alarmWayList) {
            String wechatAppName = service.getWechatAppName();
            try {
                System.err.println("================= Send Message =================");
                boolean status = wechatSendMessageAPI.sendMessage(wechatAppName, messageContent);

            } catch (IOException e) {
                e.printStackTrace();
            }
           /* switch (way) {
                case "WECHAT":
                    try {

                        if (!status) {
                            logger.error("Send message to wecaht faild. WechatAppName: " + wechatAppName + ", MessageContent: " + messageContent);
                        }
                    } catch (IOException e) {
                        logger.error("Send message to wecaht error.", e);
                    }
                    break;
                case "ROCKETCHAT":

                    break;
                default:
                    break;}*/

        }
    }


}
