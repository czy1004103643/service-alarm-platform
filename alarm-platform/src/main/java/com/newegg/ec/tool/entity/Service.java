package com.newegg.ec.tool.entity;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public class Service {

    private String serviceId;

    private String serviceName;

    private String wechatAppName;

    private String alarmRoute;

    private String description;

    private Timestamp createTime;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWechatAppName() {
        return wechatAppName;
    }

    public void setWechatAppName(String wechatAppName) {
        this.wechatAppName = wechatAppName;
    }

    public String getAlarmRoute() {
        return alarmRoute;
    }

    public void setAlarmRoute(String alarmRoute) {
        this.alarmRoute = alarmRoute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
