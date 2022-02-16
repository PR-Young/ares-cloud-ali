package com.ares.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Young
 * @date: 2020/09/01
 * @see: com.ares.core.utils JsonUtils.java
 **/
@Slf4j
public class JsonUtils {

    public static GsonBuilder builder = new GsonBuilder();
    public static Gson gson = builder.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    static {
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Date temp = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    temp = new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                } catch (Exception e) {
                    temp = new Date(jsonElement.getAsJsonPrimitive().getAsString());
                }
                return temp;
            }
        });
    }

    public static JSONObject toJsonObject(String json) {
        return JSON.parseObject(json);
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }

    public static Map<String, Object> parseJsonToMap(Object json) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == json) {
                return null;
            }
            if (json instanceof JsonObject) {
                map = gson.fromJson((JsonElement) json, new TypeToken<Map<String, Object>>() {
                }.getType());
            } else if (json instanceof JSONObject) {
                map = JSON.parseObject(json.toString(), Map.class);
            }
        } catch (Exception e) {
            log.error("parse json to map error: ", e);
            return null;
        }
        return map;
    }

}
