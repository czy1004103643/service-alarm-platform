package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.entity.MonitorData;
import org.springframework.stereotype.Repository;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Repository
public interface MonitorDataDao {

    int addMonitorData(MonitorData monitorData);
    MonitorData selectDataById(String dataId);





}
