package com.newegg.ec.tool.entity;

import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public class ServiceModel {

    private String serviceId;

    private String groupId;

    private String groupName;

    private String serviceName;

    private String wechatAppName;

    private String alarmWay;

    private String description;

    private Timestamp updateTime;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(String alarmWay) {
        this.alarmWay = alarmWay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ServiceModel{");
        sb.append("serviceId='").append(serviceId).append('\'');
        sb.append(", groupId='").append(groupId).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", serviceName='").append(serviceName).append('\'');
        sb.append(", wechatAppName='").append(wechatAppName).append('\'');
        sb.append(", alarmWay='").append(alarmWay).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
