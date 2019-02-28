import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

/**
 * @program: service-alarm-platform
 * @description: dfg
 * @create: 2019-02-28 09:42
 **/
public class JsonTest {
    public static void main(String[] args) {
        String str="{\n" +
                "    \"took\": 26,\n" +
                "    \"timed_out\": false,\n" +
                "    \"_shards\": {\n" +
                "        \"total\": 120,\n" +
                "        \"successful\": 120,\n" +
                "        \"skipped\": 0,\n" +
                "        \"failed\": 0\n" +
                "    },\n" +
                "    \"hits\": {\n" +
                "        \"total\": 6888727,\n" +
                "        \"max_score\": 0.0,\n" +
                "        \"hits\": []\n" +
                "    },\n" +
                "    \"aggregations\": {\n" +
                "        \"result\": {\n" +
                "            \"doc_count_error_upper_bound\": 0,\n" +
                "            \"sum_other_doc_count\": 0,\n" +
                "            \"buckets\": [\n" +
                "                {\n" +
                "                    \"key\": \"2019022700\",\n" +
                "                    \"doc_count\": 427516\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022701\",\n" +
                "                    \"doc_count\": 263087\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022702\",\n" +
                "                    \"doc_count\": 482461\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022703\",\n" +
                "                    \"doc_count\": 515311\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022704\",\n" +
                "                    \"doc_count\": 367380\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022705\",\n" +
                "                    \"doc_count\": 322354\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022706\",\n" +
                "                    \"doc_count\": 305022\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022707\",\n" +
                "                    \"doc_count\": 355019\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022708\",\n" +
                "                    \"doc_count\": 326247\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022709\",\n" +
                "                    \"doc_count\": 322491\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022710\",\n" +
                "                    \"doc_count\": 548466\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022711\",\n" +
                "                    \"doc_count\": 317930\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022712\",\n" +
                "                    \"doc_count\": 312851\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022713\",\n" +
                "                    \"doc_count\": 426815\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022714\",\n" +
                "                    \"doc_count\": 345282\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022715\",\n" +
                "                    \"doc_count\": 393805\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022716\",\n" +
                "                    \"doc_count\": 350719\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022717\",\n" +
                "                    \"doc_count\": 288280\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022718\",\n" +
                "                    \"doc_count\": 217691\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONArray authors = JsonPath.read(str, "aggregations.result.buckets");
//        DocumentContext ext = JsonPath.parse(str);
//        JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
//        ext.set(p, 141145561197333L);
        System.out.println(authors);
    }
}
