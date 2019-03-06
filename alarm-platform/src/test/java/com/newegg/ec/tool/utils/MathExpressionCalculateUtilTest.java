package com.newegg.ec.tool.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MathExpressionCalculateUtilTest {

    @Test
    public void checkRule() {
        boolean result = MathExpressionCalculateUtil.checkRule("@{response_a.time}>400");
        System.out.println(result);
        assertTrue(result);
    }

    @Test
    public void getRuleDataStr() {
        String formula = "@{responseTime}/@{count}>50";

        String formula2 = "@{count}>500";

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("responseTime", 123);
        dataMap.put("count", 100);

        /*try {
            Object calculate = MathExpressionCalculateUtil.calculate(formula, dataMap);
            System.out.println(calculate);
            Object calculate2 = MathExpressionCalculateUtil.calculate(formula2, dataMap);
            System.out.println(calculate2);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String ruleDataStr = MathExpressionCalculateUtil.getRuleDataStr(formula, dataMap);
        System.out.println(ruleDataStr);
        /*String ruleDataStr2 = MathExpressionCalculateUtil.getRuleDataStr(formula2, dataMap);
        System.out.println(ruleDataStr2);*/
    }
}