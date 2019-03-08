package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.AlarmPlatformApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class OldApiGatewayServiceTest {

    @Autowired
    private OldApiGatewayService oldApiGatewayService;

    @Test
    public void dealByUrl() {

        String url = "1d076b19-5e84-4643-81fe-2ce548f61021";
        ArrayList arrayList = oldApiGatewayService.dealByUrl(url);
        System.out.println(arrayList);
    }
}