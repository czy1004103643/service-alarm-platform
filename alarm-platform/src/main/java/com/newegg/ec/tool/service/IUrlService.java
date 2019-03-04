package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.ServiceUrl;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
public interface IUrlService {

    List<ServiceUrl> getServiceUrlList(String serviceId);

    boolean saveServiceUrl(ServiceUrl serviceUrl);

    ServiceUrl getServiceUrlById(String urlId);

    boolean deleteServiceUrlById(String urlId);

}
