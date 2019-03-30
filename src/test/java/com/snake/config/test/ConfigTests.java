package com.snake.config.test;

import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.ConfigManager;
import com.snake.tools.utils.FileStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ConfigTests {

    private JSONObject data;

    @Before
    public void setUp() throws IOException {
        this.data = FileStringUtils.readClassPathJSONObject("data/server");
    }

    @Test
    public void readParameterTest() throws IOException {
        ConfigManager configManager = ConfigManager.build("protocol").readParameter();

        assert configManager != null;
    }

}
