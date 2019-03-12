package com.newegg.ec.tool.service.impl;

import com.newegg.ec.tool.AlarmPlatformApplication;
import com.newegg.ec.tool.entity.Rule;
import com.newegg.ec.tool.service.IRuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlarmPlatformApplication.class)
public class RuleServiceTest {

    @Autowired
    private IRuleService ruleService;

    @Test
    public void checkRule() {
        Rule rule = new Rule();
        rule.setUrlId("1d076b19-5e84-4643-81fe-2ce548f61021");
        rule.setFormula("@{aggregations.result.buckets.doc_count}/@{aggregations.result.buckets.key}>20");
        boolean b = ruleService.checkRule(rule);
        System.out.println(b);
    }
}