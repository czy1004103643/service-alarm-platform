package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.MonitorDataDao;
import com.newegg.ec.tool.entity.MonitorData;
import com.newegg.ec.tool.service.IMonitorDataService;
import com.newegg.ec.tool.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/9
 */
@Service
public class MonitorDataService implements IMonitorDataService {

    private static final Logger logger = LoggerFactory.getLogger(MonitorDataService.class);

    @Autowired
    private MonitorDataDao monitorDataDao;

    @Override
    public boolean saveMonitorData(MonitorData monitorData) {
        if (monitorData == null
                || StringUtils.isBlank(monitorData.getRuleId())
                || StringUtils.isBlank(monitorData.getUrlId())) {
            return false;
        }
        try {
            String dataid = monitorData.getRuleId() + monitorData.getUrlId();
            monitorData.setDataId(String.valueOf(dataid.hashCode()));
            monitorData.setUpdateTime(CommonUtils.getCurrentTimestamp());
            return monitorDataDao.addMonitorData(monitorData) > 0;
        } catch (Exception e) {
            logger.error("save monitor data error.", e);
        }
        return false;
    }

    @Override
    public MonitorData getDataById(String dataId) {
        if (StringUtils.isBlank(dataId)) {
            return null;
        }
        try {
            return monitorDataDao.selectDataById(dataId);
        } catch (Exception e) {
            logger.error("get monitor data via id error.", e);
        }
        return null;
    }

    @Override
    public List<MonitorData> existMonitorData(String ruleId) {
        if (StringUtils.isBlank(ruleId)) {
            return null;
        }
        try {
            return monitorDataDao.existMonitorData(ruleId);
        } catch (Exception e) {
            logger.error("get monitor data via rule id error.", e);
        }
        return null;
    }

    @Override
    public List<MonitorData> getMonitorDataList(String serviceId) {
        List<MonitorData> monitorDataList = new ArrayList<>();
        if (StringUtils.isBlank(serviceId)) {
            return monitorDataList;
        }
        try {
            monitorDataList = monitorDataDao.selectDataByServiceId(serviceId);
        } catch (Exception e) {
            logger.error("get monitor data list by service id error.", e);
        }
        return monitorDataList;
    }

    public List<MonitorData> existData(String dataId) {
        if (StringUtils.isBlank(dataId)) {
            return null;
        }
        try {
            return monitorDataDao.existData(dataId);
        } catch (Exception e) {
            logger.error("get monitor data via rule id error.", e);
        }
        return null;
    }

    public boolean updataMonitorData(MonitorData monitorData) {
        if (StringUtils.isBlank(monitorData.getDataId())) {
            return false;
        }
        try {
            return monitorDataDao.updateMonitorData(monitorData.getDataId());
        } catch (Exception e) {
            logger.error("get monitor data via rule id error.", e);
        }
        return false;
    }


}
