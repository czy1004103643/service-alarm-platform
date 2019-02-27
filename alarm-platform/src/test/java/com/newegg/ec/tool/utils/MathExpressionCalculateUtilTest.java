package com.newegg.ec.tool.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathExpressionCalculateUtilTest {

    @Test
    public void checkRule() {
        boolean result = MathExpressionCalculateUtil.checkRule("@{responseTime}>400");
        System.out.println(result);
        assertTrue(result);
    }

    @Test
    public void getRuleDataStr() {
    }
}