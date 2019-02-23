package com.newegg.ec.tools.components.wechat;

import com.alibaba.fastjson.JSONObject;
import com.newegg.ec.tools.utils.httpclient.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2019/2/22
 */
public class WechatAPI {

    private WechatAPI() {}

    private static final String ACCESS_TOKEN = "access_token";

    private static String accessToken;

    private static String sendData;

    /**
     * 第一步获取到的公司的crodid
     */
    private static String cropId = "";

    /**
     * 应用的id
     */
    private static int agentId = 1000002;

    /**
     * 第一步创建应用后看到的Secret
     */
    private static String corpSecret = "";

    public static boolean checkResponse(JSONObject result){
        Integer errorCode = result.getInteger("errorcode");
        return errorCode == 0;
    }

    public static String getAccessToken() throws IOException {
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + cropId +"&corpsecret="+ corpSecret;
        String entity = HttpClientUtil.getGetResponse(url, null);
        String accessToken = JSONObject.parseObject(entity).getString(ACCESS_TOKEN);
        return accessToken;
    }

}
