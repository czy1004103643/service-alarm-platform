package com.newegg.ec.tool.entity;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public class MonitorData {

    private String dataId;

    private String serviceId;

    private String urlId;

    private String ruleId;

    private String urlDescription;

    private String ruleAlias;

    private String formula;

    private String ruleDescription;

    private String dataContent;

    private Timestamp updateTime;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public String getRuleAlias() {
        return ruleAlias;
    }

    public void setRuleAlias(String ruleAlias) {
        this.ruleAlias = ruleAlias;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MonitorData{");
        sb.append("dataId='").append(dataId).append('\'');
        sb.append(", serviceId='").append(serviceId).append('\'');
        sb.append(", urlId='").append(urlId).append('\'');
        sb.append(", ruleId='").append(ruleId).append('\'');
        sb.append(", urlDescription='").append(urlDescription).append('\'');
        sb.append(", ruleAlias='").append(ruleAlias).append('\'');
        sb.append(", formula='").append(formula).append('\'');
        sb.append(", ruleDescription='").append(ruleDescription).append('\'');
        sb.append(", dataContent='").append(dataContent).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
