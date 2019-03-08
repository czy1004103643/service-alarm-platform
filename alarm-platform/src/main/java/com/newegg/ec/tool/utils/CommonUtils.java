package com.newegg.ec.tool.utils;

import com.newegg.ec.tool.entity.MessageContent;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.entity.ServiceModel;
import com.newegg.ec.tool.entity.ServiceUrl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Jay.H.Zou
 * @date 2019/2/28
 */
public class CommonUtils {

    private CommonUtils() {}

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public static String formatTime(long time) {
        String format = CommonUtils.format.format(time);
        return format;
    }


    /**
     * 根据时间戳判断时间差，单位小时
     */
    public static  boolean getTimstapDiff(Timestamp startTimestamp,Timestamp endTimestamp){
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff=endTimestamp.getTime()-startTimestamp.getTime();
        long min = diff % nd % nh / nm;
        return min>30;
    }

}
