package com.newegg.ec.tool.notify;

import okhttp3.Response;

import java.io.IOException;

/**
 * @program: service-alarm-platform
 * @description: rocket chat client
 * @author: gz75
 * @create: 2019-02-27 13:46
 **/
public interface RocketChatClientInterface {
     Response postMessage(String url,String chanel, String text) throws IOException;
}
