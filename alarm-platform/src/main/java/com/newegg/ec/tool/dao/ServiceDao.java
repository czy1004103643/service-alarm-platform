package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.ServiceModel;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public interface ServiceDao {

    ServiceModel selectServiceById(String serviceId);

    int selectServiceByName(String serviceName);

    List<ServiceModel> selectAllService();

    int addService(ServiceModel serviceModel);

    int updateService(ServiceModel serviceModel);

    int deleteServiceById(String serviceId);
}
