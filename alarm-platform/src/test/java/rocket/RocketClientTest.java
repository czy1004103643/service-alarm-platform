package rocket;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.notify.rocket.DefaultHttpClient;
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
DefaultHttpClient defaultRocketChatClient;

    @Test
    public void Test() throws IOException {

        defaultRocketChatClient.postRocketMessage("this is testt");
      // defaultRocketChatClient.postNetMessage("http://10.1.54.179:8900/e4/api-logs/_search", "this a test");

    }
    @Test
    public void test2() throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        System.out.println(new Date());


//        Calendar cal = Calendar.getInstance();
//        Date date = cal.getTime();
//
//        //时间戳转换为时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Long lt = new Long(1551427199999L);
//        System.out.println(simpleDateFormat.format(lt));
//
//        String startTime="2019-02-28 00:00:00";
//        Date startDate = simpleDateFormat.parse(startTime);
//        Long startLong = startDate.getTime();
//
//        String stopTime="2019-02-28 23:59:59";
//        Date stopDate = simpleDateFormat.parse(stopTime);
//        Long stopLong = stopDate.getTime();
//
//        System.out.println("stopLong :" + stopLong);
//        System.out.println("startLong: " + startLong);



    }
}
