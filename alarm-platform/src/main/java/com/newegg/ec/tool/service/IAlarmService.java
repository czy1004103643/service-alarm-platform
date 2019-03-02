package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.ServiceModel;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public interface IAlarmService {

    boolean addService(ServiceModel serviceModel);

    ServiceModel updateServiceInfo(ServiceModel serviceModel);

    List<ServiceModel> getServiceModelList();

    ServiceModel getServiceModelById(String serviceId);

    boolean deleteServiceModelById(String serviceId);

}
