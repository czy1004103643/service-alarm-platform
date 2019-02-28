package com.newegg.ec.tool.notify.rocket;

import com.newegg.ec.tool.notify.RocketChatClientInterface;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: service-alarm-platform
 * @description: restful api 实现的rocat访问
 * @author: gz75
 * @create: 2019-02-27 13:51
 **/
@Component
public class DefaultRocketChatClient implements RocketChatClientInterface, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RocketConfig rocketConfig;

    private OkHttpClient httpClient;

    private Request request;

    @Override
    public Response postMessage(String url,String chanel, String data) throws IOException {
        request.newBuilder().url(url);
        MediaType mediaType = MediaType.parse("application/json");
        String str = "{ \"channel\": \"" + chanel + "\", \"text\": \"" + data + "\"}";
        RequestBody body = RequestBody.create(mediaType, str);
        request.newBuilder();
        Response response = httpClient.newCall(request).execute();
        return response;
    }

    public void configNew(String token,String userid){
          request.newBuilder()
                 .addHeader("X-Auth-Token",  rocketConfig.getToken())
                 .addHeader("X-User-Id", rocketConfig.getUserID())
                 .build();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        httpClient = new OkHttpClient();
        request = new Request.Builder()
              .url("https://localhost")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Auth-Token",  rocketConfig.getToken())
                .addHeader("X-User-Id", rocketConfig.getUserID())
                .addHeader("cache-control", "no-cache")
                .build();

    }
}
