import com.alibaba.fastjson.JSONObject;

/**
 * @author Jay.H.Zou
 * @date 2019/3/2
 */
public class TestJson {

    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "    \"took\": 424,\n" +
                "    \"timed_out\": false,\n" +
                "    \"_shards\": {\n" +
                "        \"total\": 80,\n" +
                "        \"successful\": 80,\n" +
                "        \"skipped\": 0,\n" +
                "        \"failed\": 0\n" +
                "    },\n" +
                "    \"hits\": {\n" +
                "        \"total\": 14516204,\n" +
                "        \"max_score\": 0,\n" +
                "        \"hits\": []\n" +
                "    },\n" +
                "    \"aggregations\": {\n" +
                "        \"result\": {\n" +
                "            \"doc_count_error_upper_bound\": 0,\n" +
                "            \"sum_other_doc_count\": 0,\n" +
                "            \"buckets\": [\n" +
                "                {\n" +
                "                    \"key\": \"2019022000\",\n" +
                "                    \"doc_count\": 725556\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022001\",\n" +
                "                    \"doc_count\": 776835\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019022002\",\n" +
                "                    \"doc_count\": 793158\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String s = JSONObject.parseObject(jsonStr).toString();
        System.out.println(s);
    }
}
