package com.snake.config.test;

import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.ConfigManager;
import com.snake.data.config.Parameter;
import com.snake.data.json.FastJsonValueProvider;
import com.snake.data.translate.ParameterParser;
import com.snake.data.translate.Config;
import com.snake.data.translate.TranslateManager;
import com.snake.tools.utils.FileStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ParameterParserTests {

    private JSONObject data;
    private ConfigManager configManager;
    private TranslateManager translateManager;

    @Before
    public void setUp() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        this.data = FileStringUtils.readClassPathJSONObject("data/server");
        this.configManager = ConfigManager.build("protocol").readParameter().buildParameterMap();
        this.translateManager = TranslateManager.build("translator");
    }

    @Test
    public void parseToByteTest() throws Exception {
//        JSONObject json = this.data.getJSONObject("command");
        Config config = new Config();
        Parameter parameter = this.configManager.getParameter("sanmeditech");
        ParameterParser parameterParser = new ParameterParser(parameter,new FastJsonValueProvider(this.data),this.translateManager);
        byte[] bytes = parameterParser.getBytes(config);
        assert bytes.length > 0;
    }

}
