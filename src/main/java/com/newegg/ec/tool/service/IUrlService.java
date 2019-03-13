package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.ServiceUrl;
import javafx.util.Pair;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
public interface IUrlService {

    List<ServiceUrl> getServiceUrlList();

    List<ServiceUrl> getServiceUrlList(String serviceId);

    boolean saveServiceUrl(ServiceUrl serviceUrl);

    boolean copyServiceUrl(ServiceUrl serviceUrl);

    ServiceUrl getServiceUrlById(String urlId);

    boolean deleteServiceUrlById(String urlId);

    boolean deleteServiceUrlByServiceId(String serviceId);

    Pair<Boolean, Object> checkUrl(ServiceUrl serviceUrl);

    boolean checkRequestParam(ServiceUrl serviceUrl);

}
