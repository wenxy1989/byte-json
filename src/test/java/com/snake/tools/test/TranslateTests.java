package com.snake.tools.test;

import com.snake.data.translate.Config;
import com.snake.data.translate.TranslateChain;
import com.snake.data.translate.TranslatorManager;
import com.snake.data.config.Type;
import com.snake.data.translate.function.ByteStringTranslator;
import com.snake.data.translate.function.ByteFullLengthTranslator;
import com.snake.data.translate.function.StringByteTranslator;
import com.snake.data.translate.function.StringTrimTranslator;
import org.junit.Before;
import org.junit.Test;

public class TranslateTests {

    private TranslatorManager manager;

    @Before
    public void setup() {
        manager = new TranslatorManager();
        manager.addTranslator(new ByteStringTranslator());
        manager.addTranslator(new ByteFullLengthTranslator());
        manager.addTranslator(new StringByteTranslator());
        manager.addTranslator(new StringTrimTranslator());
    }

    @Test
    public void translatorChainTest() throws Exception {
        Type type = new Type();
        type.setCode("fillString");
        type.setJavaType("string");
        type.setJsonType("string");
        type.setByteLength(40);
        type.setTranslateToByteChain("string-byte", "byte-full-length");
        type.setTranslateByteChain("byte-string", "string-trim");
        Config config = new Config(type, true);
        TranslateChain toByteChain = this.manager.getTranslateChain(config,type.getTranslateToByteChain());
        TranslateChain byteChain = this.manager.getTranslateChain(config,type.getTranslateByteChain());


        String data = "12321321";
//        System.out.println(data);
        toByteChain.setInput(data);
        toByteChain.translate();
        byte[] bytes = (byte[]) toByteChain.getOutput();
        byteChain.setInput(bytes);
        byteChain.translate();
        String str = (String) byteChain.getOutput();
//        System.out.println(str);
//        System.out.println("        " +str);
        assert str.equals(data);
    }

}
