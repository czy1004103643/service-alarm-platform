package com.newegg.ec.tool.utils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
}
