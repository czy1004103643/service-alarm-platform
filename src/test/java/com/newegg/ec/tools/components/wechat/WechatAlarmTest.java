package com.newegg.ec.tools.components.wechat;

import org.junit.Test;

import java.io.IOException;

public class WechatAlarmTest {

    @Test
    public void getAccessToken() throws IOException {
        String accessToken = WechatAPI.getAccessToken();
        System.err.println("\n" + accessToken);
    }
}