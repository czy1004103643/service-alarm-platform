package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.notify.wechat.entity.WechatAppInfo;
import com.newegg.ec.tool.service.IAppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
@Controller
@RequestMapping("/service/*")
public class AppServiceController {

    @Autowired
    private IAppService appService;

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    @RequestMapping(value = "/getServiceModelList", method = RequestMethod.GET)
    @ResponseBody
    public Result getServiceModelList(@PathParam("groupId") String groupId) {
        List<ServiceModel> serviceModelList = appService.getServiceModelList(groupId);
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

    @RequestMapping(value = "/getWechatAppList", method = RequestMethod.GET)
    @ResponseBody
    public Result getWechatAppList() {
        List<WechatAppInfo> appList = wechatBaseInfoConfig.getAppList();
        List<String> appNameList = new LinkedList<>();
        for (WechatAppInfo wechatAppInfo : appList) {
            String appName = wechatAppInfo.getAppName();
            if (StringUtils.isNotBlank(appName)) {
                appNameList.add(appName);
            }
        }
        return Result.successResult(appNameList);
    }

}
