package com.newegg.ec.tool.controller;

import com.newegg.ec.tool.entity.Result;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IUrlService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
@Controller
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private IUrlService urlService;

    @RequestMapping(value = "/getUrlList", method = RequestMethod.GET)
    @ResponseBody
    public Result getUrlListByServiceId(@PathParam("serviceId") String serviceId) {
        List<ServiceUrl> serviceUrlList = urlService.getServiceUrlList(serviceId);
        return Result.successResult(serviceUrlList);
    }

    @RequestMapping(value = "/getUrlById", method = RequestMethod.GET)
    @ResponseBody
    public Result getUrlById(@PathParam("urlId") String urlId) {
        ServiceUrl url = urlService.getServiceUrlById(urlId);
        return Result.successResult(url);
    }

    @RequestMapping(value = "/saveUrl", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUrl(@RequestBody ServiceUrl serviceUrl) {
        urlService.saveServiceUrl(serviceUrl);
        return Result.successResult(serviceUrl);
    }

    @RequestMapping(value = "/checkUrl", method = RequestMethod.POST)
    @ResponseBody
    public Result checkUrl(@RequestBody ServiceUrl serviceUrl) {
        Pair<Boolean, Object> statusAndResponse = urlService.checkUrl(serviceUrl);
        return Result.successResult(statusAndResponse);
    }

    @RequestMapping(value = "/deleteUrlById", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUrlById(String urlId) {
        boolean status = urlService.deleteServiceUrlById(urlId);
        return status ? Result.successResult() : Result.successResult();
    }
}
