package com.newegg.ec.tool.notify.rocket;

import com.newegg.ec.tool.notify.RocketChatClientInterface;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @program: service-alarm-platform
 * @description: restful api 实现的rocat访问
 * @author: gz75
 * @create: 2019-02-27 13:51
 **/
public class DefaultRocketChatClient implements RocketChatClientInterface {
    @Autowired
    RocketConfig rocketConfig;
    private OkHttpClient httpClient;
    private Request request;
    private static  DefaultRocketChatClient  rocketChatClient;
    public static DefaultRocketChatClient getInstance() {
        if (rocketChatClient == null) {
            rocketChatClient = new DefaultRocketChatClient();
        }
        return rocketChatClient;
    }


    @Override
    public int postMessage(String chanel,String data) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String str = "{ \"channel\": \"" + chanel + "\", \"text\": \"" + data + "\"}";
        RequestBody body = RequestBody.create(mediaType, str);
        Response response = httpClient.newCall(request).execute();
        return response.code();
    }

    private DefaultRocketChatClient(){
        httpClient = new OkHttpClient();
        /*request = new Request.Builder()
                .url("wss://chat.newegg.org/")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Auth-Token",  rocketConfig.getToken())
                .addHeader("X-User-Id", rocketConfig.getUserID())
                .addHeader("cache-control", "no-cache")
                .build();*/
    }

    public void configNew(String token,String userid){
         request.newBuilder()
                 .addHeader("X-Auth-Token",  rocketConfig.getToken())
                 .addHeader("X-User-Id", rocketConfig.getUserID())
                 .build();
    }
}
