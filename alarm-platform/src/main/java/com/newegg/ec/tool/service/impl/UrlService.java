package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IUrlService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
@Service
public class UrlService implements IUrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    private ServiceUrlDao serviceUrlDao;

    @Override
    public List<ServiceUrl> getServiceUrlList(String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return null;
        }
        try {
            return serviceUrlDao.selectUrlByServiceId(serviceId);
        } catch (Exception e) {
            logger.error("get url list error.", e);
            return null;
        }
    }

    @Override
    public boolean saveServiceUrl(ServiceUrl serviceUrl) {
        if (serviceUrl == null) {
            return false;
        }
        String serviceId = serviceUrl.getServiceId();
        if (StringUtils.isBlank(serviceId)) {
            return false;
        }
        try {
            String urlId = serviceUrl.getUrlId();
            serviceUrl.setUpdateTime(CommonUtils.getCurrentTimestamp());
            if (StringUtils.isBlank(urlId)) {
                serviceUrl.setUrlId(CommonUtils.getUUID());
                return serviceUrlDao.addServiceUrl(serviceUrl) > 0;
            }
            return serviceUrlDao.updateServiceUrl(serviceUrl) > 0;
        } catch (Exception e) {
            logger.error("save url error.", e);
        }
        return false;
    }

    @Override
    public ServiceUrl getServiceUrlById(String urlId) {
        return null;
    }

    @Override
    public boolean deleteServiceUrlById(String urlId) {
        return false;
    }
}
