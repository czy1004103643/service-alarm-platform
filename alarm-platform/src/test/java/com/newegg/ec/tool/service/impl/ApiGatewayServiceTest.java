package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.AlarmPlatformApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class ApiGatewayServiceTest {

    @Autowired
    private ApiGatewayService apiGatewayService;

    @Test
    public void dealByUrl() {

        String url = "1d076b19-5e84-4643-81fe-2ce548f61021";
        ArrayList arrayList = apiGatewayService.dealByUrl(url);
        System.out.println(arrayList);
    }
}