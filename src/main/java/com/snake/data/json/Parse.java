package com.snake.data.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Parameter;
import com.snake.data.translate.Config;
import com.snake.data.translate.TranslateChain;
import com.snake.data.translate.TranslatorManager;
import com.snake.tools.utils.ByteUtil;

import java.util.List;
import java.util.Map;

public class Parse {

    private Parameter config;
    private JSONObject data;
    private TranslatorManager manager;

    public Parse(Parameter config,JSONObject data,TranslatorManager manager){
        this.config = config;
        this.data = data;
        this.manager = manager;
    }

    public boolean isJsonObject(){
        return this.data.get(this.config.getCode()) instanceof Map;
    }

    public boolean isJsonArray(){
        return this.data.get(this.config.getCode()) instanceof List;
    }

    public byte[] getModelBytes(JSONObject json) throws Exception {
        byte[] bytes = new byte[0];
        for(int i=0;i<config.getModelContent().length;i++){
            Parameter parameter = this.config.getModelContent()[i];
            Parse parse = new Parse(parameter,json,this.manager);
            bytes = ByteUtil.concat(bytes,parse.getBytes());
        }
        return bytes;
    }

    public Config getConfig(){
        return new Config(this.config.getParameterType(),true);
    }

    public TranslateChain getTranslateByteChain() throws Exception {
        return this.manager.getTranslateChain(getConfig(),this.config.getParameterType().getTranslateByteChain());
    }

    public TranslateChain getTranslateToByteChain() throws Exception {
        return this.manager.getTranslateChain(getConfig(),this.config.getParameterType().getTranslateToByteChain());
    }

    public byte[] getBytes() throws Exception {
        Object value = this.data.get(this.config.getCode());
        Object input = null;
        if(this.config.isModel()){
            if(this.isJsonObject()){
                input = getModelBytes((JSONObject) value);
            }else if(this.isJsonArray()){
                JSONArray array = (JSONArray) value;
                byte[] bytes = new byte[0];
                for(int i=0;i<this.data.getJSONArray(this.config.getCode()).size();i++){
                    bytes = ByteUtil.concat(bytes,getModelBytes(array.getJSONObject(i)));
                }
                input = bytes;
            }
            throw new Exception(this.data.toJSONString()+ "  error for key : " + this.config.getCode());
        }else{
            input = value;
        }
        TranslateChain chain = getTranslateToByteChain();
        chain.setInput(input);
        chain.translate();
        return (byte[]) chain.getOutput();
    }

}
