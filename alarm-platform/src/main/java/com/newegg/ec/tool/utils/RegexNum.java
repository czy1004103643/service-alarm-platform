package com.newegg.ec.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: gz75
 * @create: 2019-02-27 23:40
 **/
public class RegexNum {
    //判断字符串是不是以数字开头
    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
