package com.newegg.ec.tool.service;

import com.newegg.ec.tool.entity.Rule;
import net.minidev.json.JSONArray;

import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
public interface ICollectData {

    /**
     * collect monitor data by url
     * @param urlId
     * @return
     */
    Map<Rule, JSONArray> collectData(String urlId);

}
