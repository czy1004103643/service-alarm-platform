package com.newegg.ec.tool.config.init;

import com.newegg.ec.tool.service.impl.InitializationTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @author Jay.H.Zou
 * @date 2019/2/10
 */
@Component
public class InitializationConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitializationConfig.class);

    @Autowired
    private InitializationTableService initialization;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        boolean initialization = this.initialization.initialization();
        if (initialization) {
            logger.info("******************** init tables success ********************");
        } else {
            logger.error("******************** init tables failed ********************");
        }
    }

}
