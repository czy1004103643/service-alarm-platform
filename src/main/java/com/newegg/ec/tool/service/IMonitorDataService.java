package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.MonitorData;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/9
 */
public interface IMonitorDataService {

    boolean saveMonitorData(MonitorData monitorData);

    MonitorData getDataById(String dataId);

    List<MonitorData> existMonitorData(String ruleId);

}
