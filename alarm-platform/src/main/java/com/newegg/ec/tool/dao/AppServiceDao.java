package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.ServiceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Repository
public interface AppServiceDao {

    ServiceModel selectServiceById(String serviceId);

    ServiceModel selectServiceByName(@Param("serviceId") String serviceId, @Param("serviceName") String serviceName);

    List<ServiceModel> selectAllService();

    int addService(ServiceModel serviceModel);

    int updateService(ServiceModel serviceModel);

    int deleteServiceById(String serviceId);
}
