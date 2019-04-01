package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Parameter;
import com.snake.data.translate.Config;
import com.snake.data.translate.TranslateChain;
import com.snake.data.translate.TranslateManager;
import com.snake.tools.utils.ByteUtil;

import java.util.List;
import java.util.Map;

public class ParameterParser {

    private Parameter parameter;
    private JSONObject data;
    private TranslateManager manager;

    public ParameterParser(Parameter parameter, JSONObject data, TranslateManager manager) {
        this.parameter = parameter;
        this.data = data;
        this.manager = manager;
    }

    public boolean isJsonObject() {
        return this.data.get(this.parameter.getCode()) instanceof Map;
    }

    public boolean isJsonArray() {
        return this.data.get(this.parameter.getCode()) instanceof List;
    }

    public byte[] getModelBytes(JSONObject json,Config config) throws Exception {
        byte[] bytes = new byte[0];
        for (int i = 0; i < parameter.getModelContent().length; i++) {
            Parameter parameter = this.parameter.getModelContent()[i];
            ParameterParser parameterParser = new ParameterParser(parameter, json, this.manager);
            bytes = ByteUtil.concat(bytes, parameterParser.getBytes(config));
        }
        return bytes;
    }

    public TranslateChain getTranslateByteChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateByteChain());
    }

    public TranslateChain getTranslateToByteChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateToByteChain());
    }

    public byte[] getBytes(Config config) throws Exception {
        Object value = this.data.get(this.parameter.getCode());
        Object input = null;
        if (this.parameter.isModel()) {
            if (this.isJsonObject()) {
                input = getModelBytes((JSONObject) value,config);
            } else if (this.isJsonArray()) {
                JSONArray array = (JSONArray) value;
                byte[] bytes = new byte[0];
                for (int i = 0; i < this.data.getJSONArray(this.parameter.getCode()).size(); i++) {
                    bytes = ByteUtil.concat(bytes, getModelBytes(array.getJSONObject(i),config));
                }
                input = bytes;
            }
            throw new Exception(this.data.toJSONString() + "  error for key : " + this.parameter.getCode());
        } else {
            input = value;
        }
        TranslateChain chain = getTranslateToByteChain(config);
        return (byte[]) chain.translate(input);
    }

}
