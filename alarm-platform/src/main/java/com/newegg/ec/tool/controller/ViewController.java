package com.newegg.ec.tool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jay.H.Zou
 * @date 2019/2/26
 */
@Controller
public class ViewController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
