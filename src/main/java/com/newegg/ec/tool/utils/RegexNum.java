package com.newegg.ec.tool.utils;

import com.newegg.ec.tool.entity.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.newegg.ec.tool.utils.MathExpressionCalculateUtil.VAR_PATTERN;

/**
 * @description:
 * @author: gz75
 * @create: 2019-02-27 23:40
 **/
public class RegexNum {

    private static final Logger logger = LoggerFactory.getLogger(RegexNum.class);

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
        String[] strings = strs.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        if (strs != null) {
            for (int i = 0; i < strings.length - 1; i++) {
                stringBuilder.append(strings[i]);
            }
        }
        return stringBuilder.toString();
    }


    public static String getFormulaKey(String formula) {
        List<String> formulaKeyList = getFormulaKeyList(formula);
        return formulaKeyList.size() > 0 ? formulaKeyList.get(0) : null;
    }

    public static String getRealKey(String formula) {
        Pattern pattern = Pattern.compile(".*@\\.(.*)(<|>|==|!=)");
        Matcher m = pattern.matcher(formula);
        String realKey = null;
        if (m.find()) {
            realKey = m.group(1).trim();
        } else {
            logger.error("============== No match ==============" + formula);
        }
        return realKey;
    }


    public static List<String> getFormulaKeyList(String formula) {
        Matcher matcher = VAR_PATTERN.matcher(formula);
        List<String> unitFormulaList = new LinkedList<>();
        while (matcher.find()) {
            String matcherStr = formula.substring(matcher.start(), matcher.end());
            String field = matcherStr.substring(2, matcherStr.length() - 1);
            unitFormulaList.add(field);
        }
        return unitFormulaList;
    }


}
