package com.snake.config.test;

import com.snake.data.translate.Config;
import com.snake.data.translate.TranslateChain;
import com.snake.data.translate.TranslateManager;
import com.snake.data.config.ParameterType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TranslateTests {

    private TranslateManager manager;

    @Before
    public void setup() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        manager = TranslateManager.build("translator");
    }

    @Test
    public void translatorChainTest() throws Exception {
        ParameterType parameterType = new ParameterType();
        parameterType.setCode("fillString");
        parameterType.setJavaType("string");
        parameterType.setJsonType("string");
        parameterType.setByteLength(40);
        parameterType.setTranslateToByteChain("string-byte", "byte-full-length");
        parameterType.setTranslateByteChain("byte-string", "string-trim");
        Config config = new Config(true);
        config.setParameterType(parameterType);
        TranslateChain toByteChain = this.manager.getTranslateChain(config, parameterType.getTranslateToByteChain());
        TranslateChain byteChain = this.manager.getTranslateChain(config, parameterType.getTranslateByteChain());


        String data = "12321321";
        byte[] bytes = (byte[]) toByteChain.translate(data);
        String str = (String) byteChain.translate(bytes);
        assert str.equals(data);
    }

}
