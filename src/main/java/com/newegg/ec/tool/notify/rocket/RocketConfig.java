package com.newegg.ec.tool.notify.rocket;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @program: service-alarm-platform
 * @description: rocke tchient  config
 * @author: gz75
 * @create: 2019-02-27 09:52
 **/
@Configuration
@ConfigurationProperties(prefix = "alarm.notify.rocketchat", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class RocketConfig {

    private String token;

    private String userID;

    private String chanel;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

}
