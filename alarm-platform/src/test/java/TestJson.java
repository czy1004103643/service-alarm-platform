import java.util.HashMap;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public class TestJson {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("id", "345");
        map.put("name", "zou");
        System.out.println(map);
    }
}
