package com.newegg.ec.tool.wechat.config;

import com.newegg.ec.tool.wechat.WechatApplication;
import com.newegg.ec.tool.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.wechat.entity.MessageContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WechatApplication.class)
//@TestPropertySource("classpath:wechat.properties")
public class WechatBaseInfoConfigTest {

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    @Test
    public void getCorpId() {
        System.out.println(wechatBaseInfoConfig.getCorpId());
    }

    @Test
    public void testSendTextMessage() {
        MessageContent messageContent = new MessageContent();
        messageContent.setContent("Test Message from IDEA");
        try {
            System.out.println(wechatSendMessageAPI.sendMessage("ItemService", messageContent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}