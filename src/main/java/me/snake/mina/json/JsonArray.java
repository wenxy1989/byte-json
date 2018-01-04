package me.snake.mina.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by HP on 2017/12/27.
 */
public class JsonArray extends ArrayList<Object> {

    public JsonArray() {

    }
    public JsonArray(Collection<Object> data) {
        super(data);
    }

    public JsonObject getJsonObject(int index) {
        JsonObject value = null;
        Object obj = super.get(index);
        if (null != obj && obj instanceof Map) {
            value = new JsonObject((Map) obj);
        }else{
            value = new JsonObject();
        }
        return value;
    }
}
