package com.newegg.ec.tool.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
@SpringBootApplication
//@ImportResource("classpath:wechat.properties")
public class WechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }

}
