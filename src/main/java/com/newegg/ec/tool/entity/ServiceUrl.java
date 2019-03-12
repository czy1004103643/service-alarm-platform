package com.newegg.ec.tool.entity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public class ServiceUrl {

    private String urlId;

    private String serviceId;

    private String urlContent;

    /**
     * TODO: 定义枚举
     */
    private String requestType;

    private String paramContent;

    private String bodyContent;

    private String description;

    private Timestamp updateTime;

    public ServiceUrl() {}

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getParamContent() {
        return paramContent;
    }

    public void setParamContent(String paramContent) {
        this.paramContent = paramContent;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
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
        final StringBuffer sb = new StringBuffer("ServiceUrl{");
        sb.append("urlId='").append(urlId).append('\'');
        sb.append(", serviceId='").append(serviceId).append('\'');
        sb.append(", urlContent='").append(urlContent).append('\'');
        sb.append(", requestType='").append(requestType).append('\'');
        sb.append(", paramContent='").append(paramContent).append('\'');
        sb.append(", bodyContent='").append(bodyContent).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
