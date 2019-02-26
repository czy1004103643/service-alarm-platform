package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.Service;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public interface ServiceDao {

    int selectServiceById(String serviceId);

    int selectServiceByName(String serviceName);

    List<Service> selectAllService();

    int addService(Service service);

    int updateService(Service service);

    int deleteServiceById(String serviceId);
}
