package com.snake.data.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.tools.utils.FileStringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigManager {

    private Parameter[] parameters;
    private Map<String, Type> typeMap;
    private Map<String, Parameter> parameterMap;

    private JSONObject config;

    public ConfigManager() {
        this.config = new JSONObject();
        this.typeMap = new HashMap<>();
        this.parameterMap = new HashMap<>();
    }

    public void concatConfig(JSONObject json) throws Exception {
        if (null != json) {
            Iterator<String> iterator = json.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (this.config.containsKey(key)) {
                    throw new Exception("duplicate key : " + key);
                }
                this.config.put(key, json.get(key));
            }
        }
    }

    public static ConfigManager build(String... jsonFileArray) {
        ConfigManager configManager = new ConfigManager();
        for (int i = 0; i < jsonFileArray.length; i++) {
            try {
                JSONObject json = FileStringUtils.readClassPathJSONObject(jsonFileArray[i]);
                configManager.concatConfig(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return configManager;
    }


    public ConfigManager readParameter() throws IOException {
        this.parameters = parseParameterArray(this.config.getJSONArray("parameter"));
        return this;
    }

    public ConfigManager buildParameterMap(){
        for(Parameter each : this.parameters){
            this.parameterMap.put(each.getCode(),each);
        }
        return this;
    }

    public Parameter getParameter(String code){
        return this.parameterMap.get(code);
    }

    public Parameter[] parseParameterArray(JSONArray array) {
        Parameter[] parameters = new Parameter[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            parameters[i] = parseParameter(json);
        }
        return parameters;
    }

    public Parameter parseParameter(JSONObject object) {
        Parameter parameter = new Parameter();
        parameter.setParameterType(parseType(object));
        if (object.containsKey("content")) {
            parameter.setModelContent(parseParameterArray(object.getJSONArray("content")));
        }
        if (object.containsKey("code")) {
            parameter.setCode(object.getString("code"));
        }
        if (object.containsKey("name")) {
            parameter.setName(object.getString("name"));
        }
        if (object.containsKey("type")) {
            parameter.setType(object.getString("type"));
        }
        return parameter;
    }

    public Type parseType(JSONObject object) {
        Type type = new Type();
        if (object.containsKey("code")) {
            type.setCode(object.getString("code"));
        }
        if (object.containsKey("byteLength")) {
            if(object.get("byteLength") instanceof Map){
                type.setLengthProvider(object.get("byteLength"));
            }else {
                type.setByteLength(object.getShortValue("byteLength"));
            }
        }
        if (object.containsKey("decimal")) {
            type.setDecimal(object.getShort("decimal"));
        }
        if (object.containsKey("javaType")) {
            type.setJavaType(object.getString("javaType"));
        }
        if (object.containsKey("jsonType")) {
            type.setJsonType(object.getString("jsonType"));
        }
        if (object.containsKey("translateToByte")) {
            type.setTranslateToByteChain(castToStringArray(object.getJSONArray("translateToByte")));
        }
        if (object.containsKey("translateToType")) {
            type.setTranslateByteChain(castToStringArray(object.getJSONArray("translateToType")));
        }
        return type;
    }

    public String[] castToStringArray(JSONArray array) {
        String[] value = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            value[i] = array.getString(i);
        }
        return value;
    }

}
