import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.newegg.ec.tool.utils.JsonUtils;
import net.minidev.json.JSONArray;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        List<String> authors = JsonPath.read(str, "$.aggregations.result.buckets[*].doc_count");
//        DocumentContext ext = JsonPath.parse(str);
//        JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
//        ext.set(p, 141145561197333L);
        System.out.println(authors);
    }

    @Test
    public void Test3(){
        DocumentContext ext = JsonPath.parse("{\"key\":176}");
        JsonPath p = JsonPath.compile("$.query.bool.must[0].range.RequestTime.lte");
        try{
            ext.set(p, "zgs");
        }catch (Exception e){

        }

        System.out.println(p+"====");
    }
    @Test
    public void Test4(){
        String json = "{\n" +
                "    \"result\": {\n" +
                "        \"doc_count_error_upper_bound\": 0,\n" +
                "        \"sum_other_doc_count\": 0,\n" +
                "        \"buckets\": [\n" +
                "            {\n" +
                "                \"key\": \"2019022700\",\n" +
                "                \"doc_count\": 1404458\n" +
                "            },\n" +
                "            {\n" +
                "                \"key\": \"2019022701\",\n" +
                "                \"doc_count\": 1227301\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        int  valueArray = JsonPath.read(json,"result.buckets[0].doc_count");
        System.out.println(valueArray);
    }

    @Test
    public void Test5(){
        JSONObject jsonObject = JSONObject.parseObject("{\n" +
                "    \"took\": 158,\n" +
                "    \"timed_out\": false,\n" +
                "    \"_shards\": {\n" +
                "        \"total\": 80,\n" +
                "        \"successful\": 80,\n" +
                "        \"skipped\": 0,\n" +
                "        \"failed\": 0\n" +
                "    },\n" +
                "    \"hits\": {\n" +
                "        \"total\": 9346049,\n" +
                "        \"max_score\": 0,\n" +
                "        \"hits\": []\n" +
                "    },\n" +
                "    \"aggregations\": {\n" +
                "        \"result\": {\n" +
                "            \"doc_count_error_upper_bound\": 0,\n" +
                "            \"sum_other_doc_count\": 0,\n" +
                "            \"buckets\": [\n" +
                "                {\n" +
                "                    \"key\": \"2019030521\",\n" +
                "                    \"doc_count\": 1563012\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030522\",\n" +
                "                    \"doc_count\": 1670482\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030523\",\n" +
                "                    \"doc_count\": 1450128\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030600\",\n" +
                "                    \"doc_count\": 1461530\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030601\",\n" +
                "                    \"doc_count\": 1295697\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030602\",\n" +
                "                    \"doc_count\": 1285202\n" +
                "                },\n" +
                "                {\n" +
                "                    \"key\": \"2019030603\",\n" +
                "                    \"doc_count\": 619998\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}");
        List<BigDecimal> bigDecimalList = JsonUtils.getValue(jsonObject, "aggregations.result.buckets.doc_count");
        System.out.println(bigDecimalList.size());
    }

    @Test
    public void Test6(){
        String str = "{\n" +
                "    \"start_time\": \"2019-03-07T22:12:44.842Z\",\n" +
                "    \"end_time\": \"2019-03-08T02:12:44.842Z\",\n" +
                "    \"filter\": \"SeverityLevel:WARN,CountryCode:USA,Domain:WWW\",\n" +
                "    \"timezone\": \"America/Los_Angeles\",\n" +
                "    \"all_count\": 166505,\n" +
                "    \"data\": {\n" +
                "        \"e4web08\": [\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 18:00\",\n" +
                "                \"timestamp\": \"1552010400000\",\n" +
                "                \"count\": \"2\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 2,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 2\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 17:40\",\n" +
                "                \"timestamp\": \"1552009200000\",\n" +
                "                \"count\": \"2\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 2,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 2\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 17:20\",\n" +
                "                \"timestamp\": \"1552008000000\",\n" +
                "                \"count\": \"0\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 0,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 0\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 17:00\",\n" +
                "                \"timestamp\": \"1552006800000\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 1,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 1\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 16:40\",\n" +
                "                \"timestamp\": \"1552005600000\",\n" +
                "                \"count\": \"3\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 3,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 3\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 16:00\",\n" +
                "                \"timestamp\": \"1552003200000\",\n" +
                "                \"count\": \"3\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 3,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 3\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 15:40\",\n" +
                "                \"timestamp\": \"1552002000000\",\n" +
                "                \"count\": \"3\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 3,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 3\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 15:20\",\n" +
                "                \"timestamp\": \"1552000800000\",\n" +
                "                \"count\": \"0\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 0,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 0\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 15:00\",\n" +
                "                \"timestamp\": \"1551999600000\",\n" +
                "                \"count\": \"0\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 0,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 0\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 14:40\",\n" +
                "                \"timestamp\": \"1551998400000\",\n" +
                "                \"count\": \"1\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 1,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 1\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"time\": \"2019-03-07 14:20\",\n" +
                "                \"timestamp\": \"1551997200000\",\n" +
                "                \"count\": \"0\",\n" +
                "                \"loglevel\": {\n" +
                "                    \"info\": 0,\n" +
                "                    \"warn\": 0,\n" +
                "                    \"error\": 0,\n" +
                "                    \"fatal\": 0,\n" +
                "                    \"unknow\": 0,\n" +
                "                    \"total\": 0\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"threshold\": {\n" +
                "        \"min\": {\n" +
                "            \"warn\": 200,\n" +
                "            \"error\": 10\n" +
                "        },\n" +
                "        \"hour\": {\n" +
                "            \"warn\": 600,\n" +
                "            \"error\": 30\n" +
                "        },\n" +
                "        \"day\": {\n" +
                "            \"warn\": 4800,\n" +
                "            \"error\": 720\n" +
                "        }\n" +
                "    }\n" +
                "}";




        JSONArray object1=JsonPath.read(str,"$.data..loglevel[?(@.warn > 1 && @.error > 1)]");


        JSONArray array=JsonPath.read(str,"$.data..time");








       // System.out.println(map.size());
        LinkedHashMap linkedHashMap= JsonPath.read(str, "$.data");

        Iterator<Map.Entry> iterator= linkedHashMap.entrySet().iterator();

        while(iterator.hasNext())
        {
            Map.Entry entry = iterator.next();
          //  System.out.println(entry.getKey()+":"+entry.getValue());
        }

        //JsonPath strs=  JsonPath.read(str, "$.data.e4web08");




    }
}
