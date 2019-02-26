package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.ServiceUrl;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public interface ServiceUrlDao {

    List<ServiceUrl> selectUrlByServiceId(String serviceId);

    int addServiceUrl(ServiceUrl serviceUrl);

    int updateServiceUrl(ServiceUrl serviceUrl);

    int deleteServiceUrlById(String urlId);

    @Deprecated
    int deleteServiceUrlsByServiceId(String serviceId);

}
