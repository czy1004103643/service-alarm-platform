package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.service.IAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
@Controller
@RequestMapping("/service/*")
public class AlarmServiceController {

    @Autowired
    private IAlarmService alarmService;

    @RequestMapping(value = "/getServiceModelList", method = RequestMethod.GET)
    @ResponseBody
    public Result getServiceModelList() {
        List<ServiceModel> serviceModelList = alarmService.getServiceModelList();
        return Result.successResult(serviceModelList);
    }

    @RequestMapping(value = "/addServiceModel", method = RequestMethod.POST)
    @ResponseBody
    public Result addServiceModel(@RequestBody ServiceModel serviceModel) {
        boolean status = alarmService.addService(serviceModel);
        return status ? Result.successResult() : Result.failResult();
    }
}
