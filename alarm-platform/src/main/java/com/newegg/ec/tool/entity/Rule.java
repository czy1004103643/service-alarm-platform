package com.newegg.ec.tool.entity;

import java.sql.Timestamp;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
public class Rule {

    private String ruleId;

    private String urlId;

    private String formula;

    private String ruleAlias;

    private Timestamp updateTime;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRuleAlias() {
        return ruleAlias;
    }

    public void setRuleAlias(String ruleAlias) {
        this.ruleAlias = ruleAlias;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Rule{");
        sb.append("ruleId='").append(ruleId).append('\'');
        sb.append(", urlId='").append(urlId).append('\'');
        sb.append(", formula='").append(formula).append('\'');
        sb.append(", ruleAlias='").append(ruleAlias).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
