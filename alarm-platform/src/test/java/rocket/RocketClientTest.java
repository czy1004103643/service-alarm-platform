package rocket;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.notify.rocket.DefaultRocketChatClient;
import com.newegg.ec.tool.notify.rocket.RocketConfig;
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
@SpringBootTest(classes = RocketClientTest.class)
public class RocketClientTest {
    @Autowired
    RocketConfig rocketConfig;

    @Test
    public void Test() throws IOException {
        DefaultRocketChatClient defaultRocketChatClient=DefaultRocketChatClient.getInstance();
        defaultRocketChatClient.postMessage("8AkGjraxsWF7spNnu", "this a test");
    }
}
