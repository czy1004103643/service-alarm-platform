package rocket;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.notify.rocket.DefaultRocketChatClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @program: service-alarm-platform
 * @description: this is a test
 * @author: gz75
 * @create: 2019-02-27 14:46
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)

public class RocketClientTest {
@Autowired
DefaultRocketChatClient defaultRocketChatClient;

    @Test
    public void Test() throws IOException {


        defaultRocketChatClient.postNetMessage("http://10.1.54.179:8900/e4/api-logs/_search", "this a test");

    }
//    @Test
//    public void test2(){
//        String str = "32wein_dfs";
//        System.out.println(str.replaceAll())
//
//
//    }
}
