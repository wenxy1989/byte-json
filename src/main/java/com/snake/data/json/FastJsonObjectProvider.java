package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Parameter;

import java.util.Collection;
import java.util.Map;

public class FastJsonObjectProvider implements ObjectProvider {

    private JSONObject object;//parameter parent json this.object

    public FastJsonObjectProvider(JSONObject object) {
        this.object = object;
    }


    public boolean getBoolean(String key) {
        return this.object.getBooleanValue(key);
    }


    public byte getByte(String key) {
        return this.object.getByteValue(key);
    }


    public char getChar(String key) {
        return (char) this.object.getShortValue(key);
    }


    public short getShort(String key) {
        return this.object.getShortValue(key);
    }


    public int getInt(String key) {
        return this.object.getIntValue(key);
    }


    public float getFloat(String key) {
        return this.object.getFloatValue(key);
    }


    public long getLong(String key) {
        return this.object.getLongValue(key);
    }


    public double getDouble(String key) {
        return this.object.getDoubleValue(key);
    }


    public String getString(String key) {
        return this.object.getString(key);
    }


    public JSONObject getJSONObject(String key) {
        return this.object.getJSONObject(key);
    }


    public JSONArray getJSONArray(String key) {
        return this.object.getJSONArray(key);
    }

    @Override
    public Object getValue(Parameter parameter) {
        if (validate(parameter)) {
            if (parameter.isArray()) {
                return new FastJsonArrayProvider(parameter, getJSONArray(parameter.getCode()));
            }
            String type = parameter.getType();
            type = type.split("-")[0];
            if ("string".equals(type)) {
                return getString(parameter.getCode());
            } else if ("double".equals(type)) {
                return getDouble(parameter.getCode());
            } else if ("long".equals(type)) {
                return getLong(parameter.getCode());
            } else if ("float".equals(type)) {
                return getFloat(parameter.getCode());
            } else if ("int".equals(type)) {
                return getInt(parameter.getCode());
            } else if ("short".equals(type)) {
                return getShort(parameter.getCode());
            } else if ("char".equals(type)) {
                return getChar(parameter.getCode());
            } else if ("byte".equals(type)) {
                return getByte(parameter.getCode());
            } else if ("bool".equals(type)) {
                return getBoolean(parameter.getCode());
            } else if ("model".equals(type)) {
                return new FastJsonObjectProvider(getJSONObject(parameter.getCode()));
            }
        }
        return null;
    }

    public boolean validate(Parameter parameter) {
        return this.object != null && parameter != null && parameter.validate();
    }

    @Override
    public boolean isObject(Parameter parameter) {
        return validate(parameter) && (object.get(parameter.getCode()) == null || this.object.get(parameter.getCode()) instanceof Map);
    }

    @Override
    public boolean isArray(Parameter parameter) {
        return validate(parameter) && (null == this.object.get(parameter.getCode()) || this.object.get(parameter.getCode()) instanceof Collection);
    }

    @Override
    public String toString() {
//        return String.format("%s %s : %s", parameter.getCode(), parameter.getType(), this.object.get(parameter.getCode()));
        return this.object.toString();
    }
}
