package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.entity.ServiceUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class ServiceModelUrlDaoTest {

    @Autowired
    private ServiceUrlDao serviceUrlDao;

    @Test
    public void selectUrlById() {
        ServiceUrl serviceUrl = serviceUrlDao.selectUrlById("001");
        System.out.println(serviceUrl);
    }

    @Test
    public void selectUrlByServiceId() {
    }

    @Test
    public void addServiceUrl() {
        ServiceUrl serviceUrl = new ServiceUrl();
        serviceUrl.setUrlId("001");
        serviceUrl.setServiceId("S001");
        serviceUrl.setUrlContent("http:555");
    }

    @Test
    public void updateServiceUrl() {
    }

    @Test
    public void deleteServiceUrlById() {
    }
}