package org.coastline.common.web.dao;

import org.coastline.common.web.CommonWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/1/28
 */
@SpringBootTest(classes = CommonWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {

    @Autowired
    private InitializationDao initializationDao;

    @Test
    public void testInitialization() {
        List existTable = initializationDao.isExistTable();
        System.out.println(existTable);
        initializationDao.createClusterTable();
    }

}
