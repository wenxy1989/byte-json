package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Parameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FastJsonValueProvider implements ValueProvider {

    private Parameter parameter;
    private JSONObject object;

    private JSONArray array;

//    public FastJsonValueProvider(Parameter parameter, JSONObject object) {
//        this.parameter = parameter;
//        this.object = object;
//    }

    public FastJsonValueProvider(JSONObject object) {
        this.object = object;
    }

    public FastJsonValueProvider(JSONArray array) {
        this.array = array;
    }

    @Override
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public boolean getBoolean(String key) {
        return this.object.getBooleanValue(key);
    }

    public boolean getBoolean(int index) {
        return this.array.getBoolean(index);
    }

    public byte getByte(String key) {
        return this.object.getByteValue(key);
    }

    public byte getByte(int index) {
        return this.array.getByteValue(index);
    }

    public char getChar(String key) {
        return (char) this.object.getShortValue(key);
    }

    public char getChar(int index) {
        return (char) this.array.getShortValue(index);
    }

    public short getShort(String key) {
        return this.object.getShortValue(key);
    }

    public short getShort(int index) {
        return this.array.getShortValue(index);
    }

    public int getInt(String key) {
        return this.object.getIntValue(key);
    }

    public int getInt(int index) {
        return this.array.getIntValue(index);
    }

    public float getFloat(String key) {
        return this.object.getFloatValue(key);
    }

    public float getFloat(int index) {
        return this.array.getFloatValue(index);
    }

    public long getLong(String key) {
        return this.object.getLongValue(key);
    }

    public long getLong(int index) {
        return this.array.getLongValue(index);
    }

    public double getDouble(String key) {
        return this.object.getDoubleValue(key);
    }

    public double getDouble(int index) {
        return this.array.getDoubleValue(index);
    }

    public String getString(String key) {
        return this.object.getString(key);
    }

    public String getString(int index) {
        return this.array.getString(index);
    }

    @Override
    public Object getValue(int index) {
        if(this.validate()) {
            String type = this.parameter.getType();
            type = type.split("-")[0];
            if ("string".equals(type)) {
                return getString(index);
            } else if ("double".equals(type)) {
                return getDouble(index);
            } else if ("long".equals(type)) {
                return getLong(index);
            } else if ("float".equals(type)) {
                return getFloat(index);
            } else if ("int".equals(type)) {
                return getInt(index);
            } else if ("short".equals(type)) {
                return getShort(index);
            } else if ("char".equals(type)) {
                return getChar(index);
            } else if ("byte".equals(type)) {
                return getByte(index);
            } else if ("bool".equals(type)) {
                return getBoolean(index);
            } else if ("model".equals(type)) {
                return new FastJsonValueProvider(this.array.getJSONObject(index));
            }
        }
        return null;
    }

    @Override
    public Object getValue() {
        assert null != this.parameter;
        assert this.parameter.getCode() != null;
        assert this.parameter.getType() != null;
        System.out.println(this.valueString());
        String type = this.parameter.getType();
        type = type.split("-")[0];
        if ("string".equals(type)) {
            return getString(this.parameter.getCode());
        } else if ("double".equals(type)) {
            return getDouble(this.parameter.getCode());
        } else if ("long".equals(type)) {
            return getLong(this.parameter.getCode());
        } else if ("float".equals(type)) {
            return getFloat(this.parameter.getCode());
        } else if ("int".equals(type)) {
            return getInt(this.parameter.getCode());
        } else if ("short".equals(type)) {
            return getShort(this.parameter.getCode());
        } else if ("char".equals(type)) {
            return getChar(this.parameter.getCode());
        } else if ("byte".equals(type)) {
            return getByte(this.parameter.getCode());
        } else if ("bool".equals(type)) {
            return getBoolean(this.parameter.getCode());
        } else if ("model".equals(type)) {
            return new FastJsonValueProvider(this.object.getJSONObject(this.parameter.getCode()));
        }
        return null;
    }

    public boolean validate() {
        return null == this.parameter || this.object == null || this.parameter.getCode() == null || this.parameter.getType() == null;
    }

    @Override
    public boolean isObject() {
        return this.validate() && (this.object.get(this.parameter.getCode()) == null || this.object.get(this.parameter.getCode()) instanceof Map);
    }

    @Override
    public boolean isArray() {
        return this.validate() && (null == this.object.get(this.parameter.getCode()) || this.object.get(this.parameter.getCode()) instanceof Collection);
    }

    @Override
    public int size() {
        return this.validate() ? (null == this.object.get(this.parameter.getCode()) && this.object.get(this.parameter.getCode()) instanceof Collection) ? ((Collection) this.object.get(this.parameter.getCode())).size() : 0 : 0;
    }

    @Override
    public String valueString() {
        return String.format("%s %s : %s", this.parameter.getCode(), this.parameter.getType(), this.object.get(this.parameter.getCode()));
    }
}
