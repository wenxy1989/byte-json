package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonDataProvider {

    private JSONObject object;

    private JSONArray array;

    public FastJsonDataProvider(JSONObject object){
        this.object = object;
    }

    public FastJsonDataProvider(JSONArray array){
        this.array = array;
    }

    public boolean getBoolean(String key){
        return this.object.getBooleanValue(key);
    }
    
    public boolean getBoolean(int index){
        return this.array.getBoolean(index);
    }

    public byte getByte(String key){
        return this.object.getByteValue(key);
    }

    public byte getByte(int index){
        return this.array.getByteValue(index);
    }

    public char getChar(String key){
        return (char) this.object.getShortValue(key);
    }

    public char getChar(int index){
        return (char)this.array.getShortValue(index);
    }

    public short getShort(String key){
        return this.object.getShortValue(key);
    }

    public short getShort(int index){
        return this.array.getShortValue(index);
    }

    public int getInt(String key){
        return this.object.getIntValue(key);
    }

    public int getInt(int index){
        return this.array.getIntValue(index);
    }

    public float getFloat(String key){
        return this.object.getFloatValue(key);
    }

    public float getFloat(int index){
        return this.array.getFloatValue(index);
    }

    public long getLong(String key){
        return this.object.getLongValue(key);
    }

    public long getLong(int index){
        return this.array.getLongValue(index);
    }

    public double getDouble(String key){
        return this.object.getDoubleValue(key);
    }

    public double getDouble(int index){
        return this.array.getDoubleValue(index);
    }

    public String getString(String key){
        return this.object.getString(key);
    }

    public String getString(int index){
        return this.array.getString(index);
    }

}
