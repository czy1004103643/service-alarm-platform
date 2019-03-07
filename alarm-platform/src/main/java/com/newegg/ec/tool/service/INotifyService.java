package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;

/**
 * @author Jay.H.Zou
 * @date 2019/2/27
 */
public interface INotifyService {

    void notifyClient(ServiceModel serviceModel, ServiceUrl url, Rule rule,String realData);

}
