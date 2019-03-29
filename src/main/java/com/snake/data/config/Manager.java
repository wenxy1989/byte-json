package com.snake.data.config;

import com.alibaba.fastjson.JSONObject;
import com.snake.tools.utils.FileStringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Manager {

    private Map<String,Type> typeMap;
    private Map<String,Parameter> parameterMap;

    public Manager(){
        this.typeMap = new HashMap<>();
        this.parameterMap = new HashMap<>();
    }

    JSONObject jsonConfig;

    public void read() throws IOException {
        jsonConfig = FileStringUtils.readClassPathJSONObject("protocol/server");
    }



}
