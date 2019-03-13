package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.ServiceModel;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public interface IAppService {

    List<ServiceModel> getServiceModelList(String groupId);

    boolean saveService(ServiceModel serviceModel);

    ServiceModel getServiceModelById(String serviceId);

    boolean deleteServiceModelById(String serviceId);

    boolean deleteServiceModelByGroupId(String groupId);

}
