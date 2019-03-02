package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.ServiceDao;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.service.IAlarmService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public class AlarmService implements IAlarmService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public boolean addService(ServiceModel serviceModel) {
        if (serviceModel != null) {
            serviceModel.setServiceId(CommonUtils.getUUID());
            serviceModel.setUpdateTime(CommonUtils.getCurrentTimestamp());
            int row = serviceDao.addService(serviceModel);
            if (row > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ServiceModel updateServiceInfo(ServiceModel serviceModel) {
        return null;
    }

    @Override
    public List<ServiceModel> getServiceModelList() {
        return serviceDao.selectAllService();
    }

    @Override
    public ServiceModel getServiceModelById(String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return null;
        }
        return serviceDao.selectServiceById(serviceId);
    }

    @Override
    public boolean deleteServiceModelById(String serviceId) {
        return false;
    }
}
