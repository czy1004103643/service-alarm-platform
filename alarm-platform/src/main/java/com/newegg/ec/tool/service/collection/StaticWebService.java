package com.newegg.ec.tool.service.collection;

import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.CollectionDataAbstract;

/**
 * @author Jay.H.Zou
 * @date 2019/3/8
 */
public class StaticWebService extends CollectionDataAbstract {

    @Override
    public ServiceUrl reprocessingRequest(ServiceUrl serviceUrl) {
        return serviceUrl;
    }

}
