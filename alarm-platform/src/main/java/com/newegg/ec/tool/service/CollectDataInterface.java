package com.newegg.ec.tool.service;

import java.util.List;
import java.util.Map;

/**
 * @author Jay.H.Zou
 * @date 2019/3/7
 */
public interface CollectDataInterface {

    /**
     * collect monitor data by url
     * @param urlId
     * @return 
     */
    List<Map<String, Object>> conllectData(String urlId);

}
