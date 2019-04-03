package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Parameter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FastJsonArrayProvider implements ArrayProvider {

    private Parameter parameter;
    private JSONArray array;// parameter collection

    public FastJsonArrayProvider(Parameter parameter,JSONArray array){
        this.parameter = parameter;
        this.array = array;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }


    public boolean getBoolean(int index) {
        return this.array.getBoolean(index);
    }


    public byte getByte(int index) {
        return this.array.getByteValue(index);
    }


    public char getChar(int index) {
        return (char) this.array.getShortValue(index);
    }


    public short getShort(int index) {
        return this.array.getShortValue(index);
    }


    public int getInt(int index) {
        return this.array.getIntValue(index);
    }


    public float getFloat(int index) {
        return this.array.getFloatValue(index);
    }


    public long getLong(int index) {
        return this.array.getLongValue(index);
    }


    public double getDouble(int index) {
        return this.array.getDoubleValue(index);
    }


    public String getString(int index) {
        return this.array.getString(index);
    }


    public JSONObject getJSONObject(int index) {
        return this.array.getJSONObject(index);
    }

    @Override
    public Object getValue(int index) {
        if (this.validate()) {
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
                return new FastJsonObjectProvider(getJSONObject(index));
            }
        }
        return null;
    }

    public boolean validate() {
        return null != this.parameter && this.array != null && this.parameter.getCode() != null && this.parameter.getType() != null;
    }

    @Override
    public boolean isObject(int index) {
        return this.validate() && (this.array.get(index) == null || this.array.get(index) instanceof Map);
    }

    @Override
    public int size() {
        return this.array != null ? array.size() : 0;
    }

    public String toString() {
        return String.format("%s %s : %s", this.parameter.getCode(), this.parameter.getType(), this.array);
    }
}
