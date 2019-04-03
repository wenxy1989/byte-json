package com.snake.config.test;

import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.ConfigManager;
import com.snake.data.config.Parameter;
import com.snake.data.json.FastJsonObjectProvider;
import com.snake.data.translate.*;
import com.snake.tools.utils.FileStringUtils;
import com.snake.tools.utils.HexByteUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ParameterTranslateTests {

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
        ParameterParser parameterParser = new ParameterParser(parameter,new FastJsonObjectProvider(this.data),this.translateManager);
        byte[] bytes = parameterParser.getBytes(config);
        assert bytes.length > 0;
        System.out.println(HexByteUtil.hexString(bytes,"-"));
        ParameterAnalysts analysts = new ParameterAnalysts(parameter,new ByteProvider(bytes),this.translateManager);
        Object object = analysts.getValue(config);
        JSONObject json = (JSONObject) JSONObject.toJSON(object);
        System.out.println(json);
        assert null != object;
    }

}
