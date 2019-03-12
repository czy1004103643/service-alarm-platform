package com.newegg.ec.tool.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IUrlService;
import com.newegg.ec.tool.utils.CommonUtils;
import com.newegg.ec.tool.utils.JsonUtils;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.newegg.ec.tool.utils.JsonUtils.jsonToParam;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
@Service
public class UrlService implements IUrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private static final String GET = "GET";

    private static final String POST = "POST";

    public static final String K_NEWEGG_ORG = "k.newegg.org";

    public static final String COOKIE = "JSESSIONID=node0ckl4d6ucq5oeg7a26fqjlzdd523.node0; _ga=GA1.2.1198764541.1542369946; _gid=GA1.2.512651626.1551750181; _hprkt=/ESQueryReportWeb/; _tid=149fgcdmsc9ze245xmhb24hy790e5e516hsxxek265eol8eio5jz; _tname=gz75|Gump.G.Zhao";


    @Autowired
    private ServiceUrlDao serviceUrlDao;

    @Override
    public List<ServiceUrl> getServiceUrlList() {
        try {
            return serviceUrlDao.selectAllUrl();
        } catch (Exception e) {
            logger.error("get url list error.", e);
            return null;
        }
    }

    @Override
    public List<ServiceUrl> getServiceUrlList(String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return null;
        }
        try {
            return serviceUrlDao.selectUrlByServiceId(serviceId);
        } catch (Exception e) {
            logger.error("get url list error.", e);
            return null;
        }
    }

    @Override
    public boolean saveServiceUrl(ServiceUrl serviceUrl) {
        if (!checkRequestParam(serviceUrl)) {
            return false;
        }
        try {
            String urlId = serviceUrl.getUrlId();
            serviceUrl.setUpdateTime(CommonUtils.getCurrentTimestamp());
            String paramContent = serviceUrl.getParamContent();
            if (StringUtils.isNotBlank(paramContent)) {
                serviceUrl.setParamContent(jsonToParam(paramContent));
            }
            if (StringUtils.isBlank(urlId)) {
                serviceUrl.setUrlId(CommonUtils.getUUID());
                return serviceUrlDao.addServiceUrl(serviceUrl) > 0;
            }
            return serviceUrlDao.updateServiceUrl(serviceUrl) > 0;
        } catch (Exception e) {
            logger.error("save url error.", e);
        }
        return false;
    }

    @Override
    public ServiceUrl getServiceUrlById(String urlId) {
        if (StringUtils.isBlank(urlId)) {
            return null;
        }
        try {
            ServiceUrl serviceUrl = serviceUrlDao.selectUrlById(urlId);
            if (serviceUrl != null && StringUtils.isNotBlank(serviceUrl.getParamContent())) {
                serviceUrl.setParamContent(JsonUtils.paramsToJson(serviceUrl.getParamContent()));
            }
            return serviceUrl;
        } catch (Exception e) {
            logger.error("get url by id error.", e);
            return null;
        }
    }

    @Override
    public boolean deleteServiceUrlById(String urlId) {
        if (StringUtils.isBlank(urlId)) {
            return false;
        }
        try {
            return serviceUrlDao.deleteServiceUrlById(urlId) > 0;
        } catch (Exception e) {
            logger.error("delete url error.", e);
            return false;
        }
    }

    @Override
    public Pair<Boolean, Object> checkUrl(ServiceUrl serviceUrl) {
        Pair<Boolean, Object> statusAndResponse = new Pair<>(false, null);
        if (!checkRequestParam(serviceUrl)) {
            return statusAndResponse;
        }
        String response = "";
        String requestType = serviceUrl.getRequestType();
        if (Objects.equals(requestType, GET)) {
            String paramContent = JsonUtils.jsonToParam(serviceUrl.getParamContent());
            String urlContent = serviceUrl.getUrlContent();
            String url = urlContent;
            if (StringUtils.isNotBlank(paramContent)) {
                url = url + "?" + JsonUtils.jsonToParam(paramContent);
            }
            try {
                Map<String, String> headers = new HashMap<>();
                if (url.contains(K_NEWEGG_ORG)) {
                    headers.put("Cookie", COOKIE);
                }
                response = HttpClientUtil.getGetResponse(url, headers);
            } catch (Exception e) {
                logger.error("check get url error.", e);
            }
        } else if (Objects.equals(requestType, POST)) {
            try {
                String bodyContent = serviceUrl.getBodyContent();
                String urlContent = serviceUrl.getUrlContent();
                 response= HttpClientUtil.getPostResponse(urlContent, JSONObject.parseObject(bodyContent));

            } catch (Exception e) {
                logger.error("check post url error.", e);
            }
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(response);
            statusAndResponse = new Pair<>(true, jsonObject);
        } catch (Exception e) {
            logger.error("response error, response: " + response, e);
        }
        return statusAndResponse;
    }

    @Override
    public boolean checkRequestParam(ServiceUrl serviceUrl) {
        if (serviceUrl == null) {
            return false;
        }
        String serviceId = serviceUrl.getServiceId();
        if (StringUtils.isBlank(serviceId)) {
            return false;
        }
        return true;
    }

}
