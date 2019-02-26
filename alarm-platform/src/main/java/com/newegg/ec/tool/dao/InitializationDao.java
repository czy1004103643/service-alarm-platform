package com.newegg.ec.tool.dao;

import org.springframework.stereotype.Repository;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Repository
public interface InitializationDao {

    void createServiceTable();

    void createServiceUrlTable();

    void createRuleTable();

    void createMonitorDataTable();
}
