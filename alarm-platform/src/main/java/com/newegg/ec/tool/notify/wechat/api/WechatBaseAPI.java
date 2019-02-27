package com.newegg.ec.tool.notify.wechat.api;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.utils.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.newegg.ec.tool.notify.wechat.entity.WechatConstant.ACCESS_TOKEN;
import static com.newegg.ec.tool.notify.wechat.entity.WechatConstant.ERRCODE;
import static com.newegg.ec.tool.notify.wechat.entity.WechatConstant.ERRMSG;

/**
 * @author Jay.H.Zou
 * @date 2019/2/23
 */
@Component
public class WechatBaseAPI {

    private static final Logger logger = LoggerFactory.getLogger(WechatBaseAPI.class);

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    public String getAccessToken(String corpId, String secret) throws IOException {
        String urlForAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + secret;
        String response = HttpClientUtil.getGetResponse(urlForAccessToken);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        JSONObject responseObj = JSONObject.parseObject(response);
        if (checkAccessToken(responseObj)) {
            return responseObj.getString(ACCESS_TOKEN);
        } else {
            String errorMsg = responseObj.getString(ERRMSG);
            logger.error("http get wechat access token error, serect: " + secret + ", errorMsg: " + errorMsg);
            return null;
        }
    }

    private boolean checkAccessToken(JSONObject response) {
        int errorCode = response.getInteger(ERRCODE);
        return errorCode == 0;
    }

}
