package rocket;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.notify.rocket.RocketHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

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
RocketHttpClient defaultRocketChatClient;

    @Test
    public void Test() throws IOException {

    }
    @Test
    public void test2() throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new Date());
    }
}
