package com.newegg.ec.tool.service;

import java.math.BigDecimal;
import java.util.List;
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
    Map<String, List<BigDecimal>> collectData(String urlId);

}