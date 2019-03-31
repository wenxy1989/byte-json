package com.snake.config.test;

import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.ConfigManager;
import com.snake.data.config.Parameter;
import com.snake.data.json.Parse;
import com.snake.data.translate.TranslatorManager;
import com.snake.tools.utils.FileStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ParseTests {

    private JSONObject data;
    private ConfigManager configManager;
    private TranslatorManager translatorManager;

    @Before
    public void setUp() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        this.data = FileStringUtils.readClassPathJSONObject("data/server");
        this.configManager = ConfigManager.build("protocol").readParameter().buildParameterMap();
        this.translatorManager = TranslatorManager.build("translator");
    }

    @Test
    public void parseToByteTest() throws Exception {
//        JSONObject json = this.data.getJSONObject("command");
        Parameter parameter = this.configManager.getParameter("sanmeditech");
        Parse parse = new Parse(parameter,this.data,this.translatorManager);
        parse.getBytes();
    }

}
