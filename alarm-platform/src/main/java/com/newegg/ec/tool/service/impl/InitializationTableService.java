package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.dao.InitializationDao;
import com.newegg.ec.tool.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jay.H.Zou
 * @date 2019/2/10
 */
@Service
public class InitializationTableService implements InitializationService {

    @Autowired
    private InitializationDao initializationDao;

    @Override
    public boolean initialization() {
        try {
            initializationDao.createServiceTable();
            initializationDao.createServiceUrlTable();
            initializationDao.createRuleTable();
            initializationDao.createMonitorDataTable();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
