package config;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.notify.wechat.api.WechatSendMessageAPI;
import com.newegg.ec.tool.notify.wechat.config.WechatBaseInfoConfig;
import com.newegg.ec.tool.notify.wechat.entity.MessageContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class WechatBaseInfoConfigTest {

    @Autowired
    private WechatBaseInfoConfig wechatBaseInfoConfig;

    @Autowired
    private WechatSendMessageAPI wechatSendMessageAPI;

    @Test
    public void getCorpId() {
        System.out.println(wechatBaseInfoConfig.getCorpId());
    }

    @Test
    public void testSendTextMessage() {
        MessageContent messageContent = new MessageContent();
        messageContent.setContent("Test Message from IDEA");
        try {
            System.out.println(wechatSendMessageAPI.sendMessage("ItemService", messageContent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}