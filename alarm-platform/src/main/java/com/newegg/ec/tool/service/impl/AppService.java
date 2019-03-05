package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.AppServiceDao;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.service.IAppService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
@Service
public class AppService implements IAppService {

    private static final Logger logger = LoggerFactory.getLogger(AppService.class);

    @Autowired
    private AppServiceDao appServiceDao;

    @Override
    public List<ServiceModel> getServiceModelList() {
        return appServiceDao.selectAllService();
    }

    @Override
    public boolean saveService(ServiceModel serviceModel) {
        if (serviceModel == null) {
            return false;
        }
        String serviceId = serviceModel.getServiceId();
        ServiceModel service = appServiceDao.selectServiceByName(serviceId, serviceModel.getServiceName());
        if (service != null) {
            return false;
        }
        serviceModel.setUpdateTime(CommonUtils.getCurrentTimestamp());
        try {
            if (StringUtils.isBlank(serviceId)) {
                serviceModel.setServiceId(CommonUtils.getUUID());
                return appServiceDao.addService(serviceModel) > 0;
            }
            return appServiceDao.updateService(serviceModel) > 0;
        } catch (Exception e) {
            logger.error("save service error.", e);
            return false;
        }
    }

    @Override
    public ServiceModel getServiceModelById(String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return null;
        }
        try {
            return appServiceDao.selectServiceById(serviceId);
        } catch (Exception e) {
            logger.error("get service by id error.", e);
            return null;
        }
    }

    @Override
    public boolean deleteServiceModelById(String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return false;
        }
        try {
            return appServiceDao.deleteServiceById(serviceId) > 0;
        } catch (Exception e) {
            logger.error("delete service error.", e);
            return false;
        }
    }
}
