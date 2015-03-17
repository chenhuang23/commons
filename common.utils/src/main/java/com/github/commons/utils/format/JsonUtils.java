/*
 * Copyright 2015 Netease Group Holding Ltd.
 *
 * 
 */
package com.github.commons.utils.format;

import com.google.gson.*;

import java.util.*;

/**
 * JsonUtils.java
 *
 * @author zhouxiaofeng 3/17/15
 */
public class JsonUtils {

    private static Gson gson = new Gson();

    public static String toJson(Object obj) {

        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> type) {

        return gson.fromJson(json, type);
    }

    /**
     * 获取JsonObject
     * 
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 根据json字符串返回Map对象
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return toMap(parseJson(json));
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext();) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray) map.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject) map.put((String) key, toMap((JsonObject) value));
            else map.put((String) key, value);
        }
        return map;
    }


    /**
     * 将JSONObjec对象转换成Properties
     *
     * @param json
     * @return
     */
    public static Properties toProperties(JsonObject json) {
        Properties properties = new Properties();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext();) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray) properties.put((String) key, toList((JsonArray) value));
            else if (value instanceof JsonObject) properties.put((String) key, toMap((JsonObject) value));
            else properties.put((String) key, value);
        }
        return properties;
    }

    /**
     * 将JSONArray对象转换成List
     * 
     * @param json
     * @return
     */
    public static List<Object> toList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(toList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(toMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }
}
