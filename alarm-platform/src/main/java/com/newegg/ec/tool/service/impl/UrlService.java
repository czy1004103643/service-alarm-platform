package com.newegg.ec.tool.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.dao.ServiceUrlDao;
import com.newegg.ec.tool.entity.ServiceUrl;
import com.newegg.ec.tool.service.IUrlService;
import com.newegg.ec.tool.utils.CommonUtils;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jay.H.Zou
 * @date 2019/3/4
 */
@Service
public class UrlService implements IUrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private static final String GET = "GET";

    private static final String POST = "POST";

    @Autowired
    private ServiceUrlDao serviceUrlDao;

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
        if (!checkRequest(serviceUrl)) {
            return false;
        }
        try {
            String urlId = serviceUrl.getUrlId();
            serviceUrl.setUpdateTime(CommonUtils.getCurrentTimestamp());
            String paramContent = serviceUrl.getParamContent();
            if (StringUtils.isNotBlank(paramContent)) {
                serviceUrl.setParamContent(buildUrlParams(paramContent));
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
            if (serviceUrl != null) {
                serviceUrl.setParamContent(paramsToJson(serviceUrl.getParamContent()));
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
        if (!checkRequest(serviceUrl)) {
            return statusAndResponse;
        }
        String requestType = serviceUrl.getRequestType();
        if (Objects.equals(requestType, GET)) {
            String paramContent = serviceUrl.getParamContent();
            String urlContent = serviceUrl.getUrlContent();
            String url = urlContent + "?" + buildUrlParams(paramContent);
            try {
                String response = HttpClientUtil.getGetResponse(url);
                statusAndResponse = new Pair<>(true, response);
            } catch (Exception e) {
                logger.error("check get url error.", e);
            }
        } else if (Objects.equals(requestType, POST)) {
            try {
                String bodyContent = serviceUrl.getBodyContent();
                String urlContent = serviceUrl.getUrlContent();
                String response = HttpClientUtil.getPostResponse(urlContent, JSONObject.parseObject(bodyContent));
                statusAndResponse = new Pair<>(true, response);
            } catch (Exception e) {
                logger.error("check post url error.", e);
            }
        }
        return statusAndResponse;
    }

    private boolean checkRequest(ServiceUrl serviceUrl) {
        if (serviceUrl == null) {
            return false;
        }
        String serviceId = serviceUrl.getServiceId();
        if (StringUtils.isBlank(serviceId)) {
            return false;
        }
        return true;
    }

    private String buildUrlParams(String paramJson) {
        StringBuffer paramContent = new StringBuffer();
        if (StringUtils.isNotBlank(paramJson)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(paramJson);
                Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
                int size = jsonObject.size();
                int index = 0;
                while (iterator.hasNext()) {
                    index++;
                    Map.Entry<String, Object> next = iterator.next();
                    paramContent.append(next.getKey()).append("=").append(next.getValue());
                    if (index < size) {
                        paramContent.append("&");
                    }
                }
            } catch (Exception e) {
                logger.error("parse json error.", e);
            }
        }
        return paramContent.toString();
    }

    private String paramsToJson(String paramContent) {
        if (StringUtils.isBlank(paramContent)) {
            return null;
        }
        String[] split = paramContent.split("\\&");
        JSONObject jsonObject = new JSONObject();
        for (String keyAndVal : split) {
            String[] keyAndValArray = keyAndVal.split("\\=");
            jsonObject.put(keyAndValArray[0], keyAndValArray[1]);
        }
        return jsonObject.toJSONString();
    }
}
