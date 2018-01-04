package me.snake.mina.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2017/12/27.
 */
public class JsonObject extends HashMap<String, Object> {

    public JsonObject() {
    }

    public JsonObject(Map<String, Object> data) {
        super(data);
    }

    public String getString(String key) {
        String value = null;
        Object obj = super.get(key);
        if (null == obj) {
            value = "";
        } else if (obj instanceof String) {
            value = (String) obj;
        } else if (obj instanceof Integer) {
            value = ((Integer) obj).toString();
        } else if (obj instanceof Double) {
            value = ((Double) obj).toString();
        } else if (obj instanceof Boolean) {
            value = ((Boolean) obj).toString();
        } else {
            value = "";
        }
        return value;
    }

    public Integer getInteger(String key) {
        Integer value = null;
        Object obj = super.get(key);
        if (null == obj) {
//            value = -1;
        } else if (obj instanceof String) {
            value = Integer.getInteger((String) obj);
        } else if (obj instanceof Integer) {
            value = (Integer) obj;
        } else if (obj instanceof Double) {
            value = ((Double) obj).intValue();
        } else if (obj instanceof Boolean) {
            value = ((Boolean) obj).booleanValue() ? 1 : 0;
        } else {
//            value = -1;
        }
        return value;
    }

    public JsonObject getJsonObject(String key) {
        JsonObject value = null;
        Object obj = super.get(key);
        if (null != obj && obj instanceof Map) {
            value = new JsonObject((Map) obj);
        } else {
            value = new JsonObject();
        }
        return value;
    }

    public JsonArray getJsonArray(String key) {
        JsonArray value = null;
        Object obj = super.get(key);
        if (null != obj && obj instanceof Collection) {
            value = new JsonArray((Collection) obj);
        } else {
            value = new JsonArray();
        }
        return value;
    }

}
