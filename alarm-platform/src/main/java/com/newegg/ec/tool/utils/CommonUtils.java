package com.newegg.ec.tool.utils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2019/2/28
 */
public class CommonUtils {

    private CommonUtils() {}

    public static List<String> stringToList(String items) {
        String[] split = items.split("\\|");
        List<String> list = Arrays.asList(split);
        return list;
    }

    public static String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int index = 0; index < size; index++) {
            String item = list.get(index);
            sb.append(item);
            if (index != size - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String formatMapToString(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            StringBuffer mapString = new StringBuffer();
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            int size = map.size();
            int index = 0;
            while (iterator.hasNext()) {
                index ++;
                Map.Entry<String, Object> next = iterator.next();
                mapString.append(next.getKey());
                mapString.append("=");
                mapString.append(next.getValue());
                if (index < size - 1) {
                    mapString.append("&");
                }
            }
            return mapString.toString();
        }
        return null;
    }
}
