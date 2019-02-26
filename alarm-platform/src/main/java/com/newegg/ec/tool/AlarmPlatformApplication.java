package com.newegg.ec.tool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author jz3y
 */
@SpringBootApplication
@ImportResource("classpath:common/beans.xml")
@MapperScan(basePackages = {"com.newegg.ec.tool"})
public class AlarmPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmPlatformApplication.class, args);
    }

}
