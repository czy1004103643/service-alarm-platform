package com.newegg.ec.tool.service.collection;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.CollectionDataAbstract;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Jay.H.Zou
 * @date 2019/3/8
 */
@Component("apiGateWayService")
public class ApiGateWayService extends CollectionDataAbstract {

    @Override
    public ServiceUrl reprocessingRequest(ServiceUrl serviceUrl) {
        try {

          //  TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            sdf2.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            String timeDay = sdf2.format(new Date());
            String startTime = timeDay + " 00:00:00";
            Date startDate = sdf.parse(startTime);
            long startTimstamp = startDate.getTime();


            String endTime = timeDay + " 23:59:59";
            Date endDate = sdf.parse(endTime);
            long endTimestamp = endDate.getTime();

            DocumentContext ext = JsonPath.parse(serviceUrl.getBodyContent());
            try {
                JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
                ext.set(p, endTimestamp);
                JsonPath p2 = JsonPath.compile("$.query.bool.must[0].range.RequestTime.gte");
                ext.set(p2, startTimstamp);
            } catch (Exception e) {
                logger.error("不符合 apiGateWay规则");
            }
            String bodyContent = ext.jsonString();
            serviceUrl.setBodyContent(bodyContent);
        } catch (Exception e) {
            logger.error("api gateway error.", e);
        }
        return serviceUrl;
    }
}
