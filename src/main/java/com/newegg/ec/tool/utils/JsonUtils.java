package com.newegg.ec.tool.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2019/3/6
 */
public class JsonUtils {

    private JsonUtils() {
    }

    public static String jsonToParam(String paramJson) {
        StringBuffer paramContent = new StringBuffer();
        if (StringUtils.isNotBlank(paramJson)) {
            JSONObject jsonObject = JSONObject.parseObject(paramJson);
            Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            int size = jsonObject.size();
            int index = 0;
            while (iterator.hasNext()) {
                index++;
                Map.Entry<String, Object> next = iterator.next();
                paramContent.append(next.getKey()).append("=").append(next.getValue());
                if (index < size) {
                    paramContent.append("&");
                }
            }

        }
        return paramContent.toString();
    }

    public static String paramsToJson(String paramContent) {
        if (StringUtils.isBlank(paramContent)) {
            return null;
        }
        String[] split = paramContent.split("\\&");
        JSONObject jsonObject = new JSONObject();
        for (String keyAndVal : split) {
            String[] keyAndValArray = keyAndVal.split("\\=");
            jsonObject.put(keyAndValArray[0], keyAndValArray[1]);
        }
        return jsonObject.toJSONString();
    }

    public static BigDecimal getSingleValue(JSONObject jsonObject, String path) {
        BigDecimal bigDecimal = null;
        String formatPath = "$." + path;
        try {
            Object read = JsonPath.read(jsonObject, formatPath);
            if (read != null) {
                bigDecimal = new BigDecimal(read.toString());
            }
        }catch (Exception e) {
            //e.printStackTrace();
        }
        return bigDecimal;
    }

    public static List<BigDecimal> getValue(JSONObject jsonObject, String path) {
        List<BigDecimal> bigDecimalList = new LinkedList<>();
        BigDecimal singleValue = getSingleValue(jsonObject, path);
        if (singleValue == null) {
            String[] split = path.split("\\.");
            if (split.length > 1) {
                String lastField = "." + split[split.length - 1];
                int length = lastField.length();
                String fixedPath = path.substring(0, path.length() - length) + "[*]" + lastField;
                try {
                    List<Object> valueList = JsonPath.read(jsonObject, "$." + fixedPath);
                    if (valueList != null && valueList.size() > 0) {
                        for (Object value : valueList) {
                            if (!Objects.equals(value, null)) {
                                try {
                                    BigDecimal bigDecimal = new BigDecimal(value.toString());
                                    bigDecimalList.add(bigDecimal);
                                } catch (Exception e) {
                                    continue;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            bigDecimalList.add(singleValue);
        }
        return bigDecimalList;
    }


    public static void main(String[] args) {
        System.out.println(getValue(JSONObject.parseObject(json), "_shards.total"));
    }

    public static final String json = "{\n" +
            "    \"took\": 8,\n" +
            "    \"timed_out\": false,\n" +
            "    \"_shards\": {\n" +
            "        \"total\": 85,\n" +
            "        \"successful\": 85,\n" +
            "        \"skipped\": 0,\n" +
            "        \"failed\": 0\n" +
            "    },\n" +
            "    \"hits\": {\n" +
            "        \"total\": 39167164,\n" +
            "        \"max_score\": 0,\n" +
            "        \"hits\": []\n" +
            "    },\n" +
            "    \"aggregations\": {\n" +
            "        \"result\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "                {\n" +
            "                    \"key\": \"2019022721\",\n" +
            "                    \"doc_count\": 1789666\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022722\",\n" +
            "                    \"doc_count\": 1652196\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022723\",\n" +
            "                    \"doc_count\": 1687143\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";
}
