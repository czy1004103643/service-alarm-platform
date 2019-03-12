import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public class TestJson {

    @Test
    public void testGetData() {
        String json = "{\n" +
                "    \"data\": 2,\n" +
                "    \"data-array\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"healthy\": true,\n" +
                "\t\t\t\t\"error\": 2,\n" +
                "\t\t\t\t\"warn\": 5,\n" +
                "\t\t\t\t\"detail\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"num\": 10,\n" +
                "\t\t\t\t\t\t\"status\": true\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"healthy\": false,\n" +
                "\t\t\t\t\"error\": 10,\n" +
                "\t\t\t\t\"warn\": 50,\n" +
                "\t\t\t\t\"detail\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"num\": 15,\n" +
                "\t\t\t\t\t\t\"status\": true\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "    ]\n" +
                "}\n";

        List<Object> read = JsonPath.read(json, "$..data-array..status");
        System.out.println(read);
    }

    @Test
    public void testOne() {
        String json = "{\n" +
                "\"errcode\": 0,\n" +
                "\"errmsg\": \"ok\",\n" +
                "\"access_token\": \"412hbgxT31XK4KPSH1covOn_HxxpetCvvkCFHA4eryUQgUAY18eJKQOU12X1xswTK2uhjylR5o3XhMxbn3B2v-lJPkD1UwcvYOPUyAHXYEJYWpQefUa_y6IHWU7tj4UAv0grwNW7fD8A37fSnHv0QKH5D5bWi58jFhrOHsyjvapHPhjuh-7X5BM-0MVBFuhwA4O97gY0FOryIdhq9ZvRTw\",\n" +
                "\"expires_in\": 7200\n" +
                "}";
        Object read = JsonPath.read(json, "$(?(@.errmsg == \"ok\"))");
        System.out.println(read);
    }
}
