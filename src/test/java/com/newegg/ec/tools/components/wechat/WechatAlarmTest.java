package com.newegg.ec.tools.components.wechat;

import org.junit.Test;

import java.io.IOException;

public class WechatAlarmTest {

    @Test
    public void getAccessToken() throws IOException {
        String accessToken = WechatAPI.getAccessToken("ww0f00cb4440ed3cf1", "Gd55K7N0q2WGEMob-PJ0oz3ePsk9zqndiDVgQyQPgk0");
        System.err.println("\n" + accessToken);
    }
}