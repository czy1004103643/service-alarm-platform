//package com.newegg.ec.rocket;
//
//import okhttp3.*;
//
//import java.io.IOException;
//
///**
// * @program: service-alarm-platform
// * @description: send message to  rocket
// * @author: Mr.Wang
// * @create: 2019-02-23 14:53
// **/
//public class RocketPost {
//public boolean postMessage(String channel,String data,String token,String userID) throws IOException {
//
//    OkHttpClient client = new OkHttpClient();
//    MediaType mediaType = MediaType.parse("application/json");
//    String str = "{ \"channel\": \"" +channel + "\", \"text\": \"" + data + "\"}";
//    RequestBody body = RequestBody.create(mediaType, str);
//    System.out.println(str);
//    Request request = new Request.Builder()
//            .url("https://chat.newegg.org/api/v1/chat.postMessage")
//            .post(body)
//            .addHeader("Accept", "application/json")
//            .addHeader("Content-Type", "application/json")
//            .addHeader("X-Auth-Token", token)
//            .addHeader("X-User-Id", userID)
//            .addHeader("cache-control", "no-cache")
//            .build();
//    Response response = client.newCall(request).execute();
//
//    return true;
//}
//}
