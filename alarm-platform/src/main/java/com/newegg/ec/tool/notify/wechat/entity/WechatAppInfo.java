package com.newegg.ec.tool.notify.wechat.entity;


/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
public class WechatAppInfo {

    private int agentId;

    private String appName;

    private String corpSecret;

    private String accessToken;

    private String users = "@all";

    public WechatAppInfo() {}

    public WechatAppInfo(int agentId, String appName, String corpSecret, String users) {
        this.agentId = agentId;
        this.appName = appName;
        this.corpSecret = corpSecret;
        this.users = users;
    }

    public WechatAppInfo(int agentId, String appName, String corpSecret, String accessToken, String users) {
        this(agentId, appName, corpSecret, users);
        this.accessToken = accessToken;

    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WechatAppInfo{");
        sb.append("agentId=").append(agentId);
        sb.append(", appName='").append(appName).append('\'');
        sb.append(", corpSecret='").append(corpSecret).append('\'');
        sb.append(", accessToken='").append(accessToken).append('\'');
        sb.append(", users='").append(users).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
