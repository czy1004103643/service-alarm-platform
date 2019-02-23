package com.newegg.ec.tool.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
/*@Configuration
@ConfigurationProperties(prefix = "wechat")
@PropertySource("classpath:wechat.properties")*/
public class WechatBaseInfoConfig {

    private static final String CORP_ID = "corpid";

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("wechat.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 企业ID
     */
    private String corpId;

    /**
     *
     */
    private String accessToken;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WechatBaseInfoConfig{");
        sb.append("corpId='").append(corpId).append('\'');
        sb.append(", accessToken='").append(accessToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
