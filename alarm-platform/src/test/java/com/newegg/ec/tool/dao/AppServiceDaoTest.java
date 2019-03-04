package com.newegg.ec.tool.dao;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.entity.ServiceModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class AppServiceDaoTest {

    @Autowired
    private AppServiceDao appServiceDao;

    @Test
    public void testSelectServiceByName() {
        ServiceModel serviceModel = appServiceDao.selectServiceByName("", "item-service");
        System.out.println(serviceModel);
    }
}
