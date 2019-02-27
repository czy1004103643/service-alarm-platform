package com.newegg.ec.tool.notify.wechat.api;

import com.github.daniel_sc.rocketchat.modern_client.RocketChatClient;
import com.github.daniel_sc.rocketchat.modern_client.response.ChatMessage;

/**
 * @program: service-alarm-platform
 * @description: test
 * @author: Mr.Wang
 * @create: 2019-02-26 18:00
 **/
public class RocketTest {
    public static void main(String[] args) {
        String url = "";

        try(RocketChatClient client = new RocketChatClient("wss://chat.newegg.org/websocket", "gz75", "Newegg@456")) {
            ChatMessage msg = client.sendMessage("Your message", "jAmcTfGvSzWoBzD7Q").join();
        }
    }

}
