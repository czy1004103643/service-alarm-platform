package com.newegg.ec.tool.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @program: service-alarm-platform
 * @description: transform
 * @author: Mr.Wang
 * @create: 2019-02-27 20:13
 **/
public class JsonObject {
    private int type;
    private JSONObject jsonObject;
    private JSONArray  jsonArray;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
