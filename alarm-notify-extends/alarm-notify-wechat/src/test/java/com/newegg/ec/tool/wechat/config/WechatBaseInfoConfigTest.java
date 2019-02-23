package com.newegg.ec.tool.wechat.config;

import com.newegg.ec.tool.wechat.WechatApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class WechatBaseInfoConfigTest {

    @Autowired
    private WechatBaseInfoConfig config;

    @Test
    public void getCorpId() {
        System.out.println(config);
    }
}