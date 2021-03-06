package com.newegg.ec.tool.notify.rocket;

import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.notify.HttpClientInterface;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RocketHttpClient implements HttpClientInterface, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger loger = LoggerFactory.getLogger(RocketHttpClient.class);

    @Autowired
    RocketConfig rocketConfig;

    private OkHttpClient httpClient;

    private Request request;

    @Override
    public Response postMessage(String url, MessageContent data)  {
        request.newBuilder().url(url);
        MediaType mediaType = MediaType.parse("application/json");
        String str = "{ \"channel\": \"" + rocketConfig.getChanel() + "\", \"text\": \"" + data.getContent() + "\"}";
        RequestBody body = RequestBody.create(mediaType, str);
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            response.close();
        } catch (IOException e) {
            loger.error("post Rocket failture",e);
        }
        return response;
    }

    @Override
    public Response getMessage(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Cookie", "")
                .build();
        Response response=null;
        try {
              response = client.newCall(request).execute();
              response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Response postNetMessage(String url,MessageContent data) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, data.getContent());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        response.close();
        return response;
    }

    public int     postRocketMessage(MessageContent data) throws IOException {


        MediaType mediaType = MediaType.parse("application/json");
        String str = "{ \"channel\": \"" + rocketConfig.getChanel() + "\", \"text\": \"" + data.getContent() + "\"}";
        System.out.println(str);
        RequestBody body = RequestBody.create(mediaType, str);
        Request request = new Request.Builder()
                .url("https://chat.newegg.org/api/v1/chat.postMessage")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("X-Auth-Token", rocketConfig.getToken())
                .addHeader("X-User-Id", rocketConfig.getUserID())
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        response.close();
        return response.code();
    }




}
