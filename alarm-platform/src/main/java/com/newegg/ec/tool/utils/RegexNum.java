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
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }



    public static String getFormula(String formula) {

        String strs = getFormulaKey(formula);
        String[] strings=strs.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        if(strs!=null){
            for (int i = 0; i < strings.length - 1; i++) {
                stringBuilder.append(strings[i]);
            }
        }
        return stringBuilder.toString();
    }

    public static String getFormulaKey(String regular) {
        String regex = "\\{(.*)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(regular);
        String strs = null;
        while (matcher.find()) {
            strs = matcher.group(1);
        }
        return strs;
    }
}
