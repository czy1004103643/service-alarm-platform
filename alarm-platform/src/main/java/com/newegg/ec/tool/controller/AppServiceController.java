package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
@Controller
@RequestMapping("/service/*")
public class AppServiceController {

    @Autowired
    private IAppService appService;

    @RequestMapping(value = "/getServiceModelList", method = RequestMethod.GET)
    @ResponseBody
    public Result getServiceModelList() {
        List<ServiceModel> serviceModelList = appService.getServiceModelList();
        return Result.successResult(serviceModelList);
    }

    @RequestMapping(value = "/saveServiceModel", method = RequestMethod.POST)
    @ResponseBody
    public Result saveServiceModel(@RequestBody ServiceModel serviceModel) {
         if (serviceModel == null) {
            return Result.badParamResult();
        }
        boolean status = appService.saveService(serviceModel);
        return status ? Result.successResult() : Result.failResult();
    }

    @RequestMapping(value = "/getServiceById", method = RequestMethod.GET)
    @ResponseBody
    public Result getServiceById(@RequestParam String serviceId) {
        ServiceModel serviceModel = appService.getServiceModelById(serviceId);
        return serviceModel != null ? Result.successResult(serviceModel) : Result.failResult();
    }

    @RequestMapping(value = "/deleteServiceById", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteServiceById(String serviceId) {
        boolean status = appService.deleteServiceModelById(serviceId);
        return status ? Result.successResult() : Result.successResult();
    }

}
