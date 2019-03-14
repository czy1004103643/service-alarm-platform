package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.MonitorData;
import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.service.impl.MonitorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/14
 */
@Controller
@RequestMapping("/monitor/*")
public class MonitorDataController {

    @Autowired
    private MonitorDataService monitorDataService;

    @RequestMapping(value = "getMonitorDataList", method = RequestMethod.GET)
    @ResponseBody
    public Result getMonitorDataList(@PathParam("serviceId") String serviceId) {
        List<MonitorData> monitorDataList = monitorDataService.getMonitorDataList(serviceId);
        return Result.successResult(monitorDataList);
    }
}
