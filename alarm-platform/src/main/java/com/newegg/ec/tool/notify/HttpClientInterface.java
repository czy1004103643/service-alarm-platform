package com.newegg.ec.tool.notify;

import com.newegg.ec.tool.entity.MessageContent;
import okhttp3.Response;

/**
 * @program: service-alarm-platform
 * @description: rocket chat client
 * @author: gz75
 * @create: 2019-02-27 13:46
 **/
public interface HttpClientInterface {

    Response postMessage(String url, MessageContent content);

    Response getMessage(String url);
}
