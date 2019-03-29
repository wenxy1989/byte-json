package com.snake.tools.test;

import com.snake.data.translate.TranslateChain;
import com.snake.data.translate.TranslatorManager;
import com.snake.data.config.Type;
import com.snake.data.translate.function.ByteStringTranslator;
import com.snake.data.translate.function.FIllBlankTranslator;
import com.snake.data.translate.function.StringByteTranslator;
import com.snake.data.translate.function.StringTrimTranslator;
import org.junit.Before;
import org.junit.Test;

public class TranslateTests {

    private TranslatorManager manager;

    @Before
    public void setup(){
        manager = new TranslatorManager();
        manager.addTranslator(new ByteStringTranslator());
        manager.addTranslator(new FIllBlankTranslator());
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
        type.setTranslatorChain("string-byte","byte-fill-blank","byte-string","string-trim");
        TranslateChain chain = this.manager.getTranslateChain(type,true);


        String data = "12321321";
//        System.out.println(data);
        chain.setInput(data);
        chain.translate();
        String str = (String) chain.getOutput();
//        System.out.println(str);
//        System.out.println("        " +str);
        assert str.equals(data);
    }

}
