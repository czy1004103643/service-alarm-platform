package com.newegg.ec.tool.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jay.H.Zou
 * @date 2019/3/6
 */
public class JsonUtils {

    private JsonUtils() {
    }

    public static boolean isExistField(JSONObject jsonObject, String path) {
        // path: a.b.c.d
        String formatPath = "$." + path;
        try {
            Object read = JsonPath.read(jsonObject, formatPath);
            System.err.println(read);
            if (read != null) {
                try {
                    new BigDecimal(read.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    try {
                        JSONArray.parseArray(read.toString());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return false;
                    }
                    // aggregations.result.buckets[0].doc_count
                    String[] split = path.split("\\.");
                    if (split.length > 1) {
                        String lastField = "." + split[split.length - 1];
                        int length = lastField.length();
                        String fixedPath = path.substring(0, path.length() - length) + "[0]" + lastField;
                        return isExistField(jsonObject, fixedPath);
                    } else if (split.length > 0) {
                        String fixedPath = path + "[0]";
                        return isExistField(jsonObject, fixedPath);
                    }
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
                    List<Object> valueList = JsonPath.read(json, "$." + fixedPath);
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
        System.out.println(getValue(JSONObject.parseObject(json), "aggregations.result.buckets.doc_count"));
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
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022800\",\n" +
            "                    \"doc_count\": 1595655\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022801\",\n" +
            "                    \"doc_count\": 1694009\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022802\",\n" +
            "                    \"doc_count\": 1548489\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022803\",\n" +
            "                    \"doc_count\": 1479233\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022804\",\n" +
            "                    \"doc_count\": 1687307\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022805\",\n" +
            "                    \"doc_count\": 1823456\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022806\",\n" +
            "                    \"doc_count\": 1833714\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022807\",\n" +
            "                    \"doc_count\": 1841053\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022808\",\n" +
            "                    \"doc_count\": 1806567\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022809\",\n" +
            "                    \"doc_count\": 2100935\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022810\",\n" +
            "                    \"doc_count\": 1514043\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022811\",\n" +
            "                    \"doc_count\": 1512473\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022812\",\n" +
            "                    \"doc_count\": 1459400\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022813\",\n" +
            "                    \"doc_count\": 1494264\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022814\",\n" +
            "                    \"doc_count\": 1427535\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022815\",\n" +
            "                    \"doc_count\": 1238482\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022816\",\n" +
            "                    \"doc_count\": 1341230\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022817\",\n" +
            "                    \"doc_count\": 1709512\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022818\",\n" +
            "                    \"doc_count\": 1821394\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022819\",\n" +
            "                    \"doc_count\": 1667107\n" +
            "                },\n" +
            "                {\n" +
            "                    \"key\": \"2019022820\",\n" +
            "                    \"doc_count\": 1442301\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}";
}
