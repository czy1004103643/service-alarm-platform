package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.ServiceModel;

/**
 * @author Jay.H.Zou
 * @date 2019/2/27
 */
public interface INotifyService {

    void notifyClient(ServiceModel serviceModel, MessageContent messageContent);

}
