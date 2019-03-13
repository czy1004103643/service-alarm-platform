package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.ServiceUrl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Repository
public interface ServiceUrlDao {

    ServiceUrl selectUrlById(String urlId);

    List<ServiceUrl> selectUrlByServiceId(String serviceId);

    List<ServiceUrl> selectAllUrl();

    int addServiceUrl(ServiceUrl serviceUrl);

    int updateServiceUrl(ServiceUrl serviceUrl);

    int deleteServiceUrlById(String urlId);

    int deleteServiceUrlByServiceId(String serviceId);

}
