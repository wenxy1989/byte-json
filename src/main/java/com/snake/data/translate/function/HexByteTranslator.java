package com.snake.data.translate.function;

import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;
import com.snake.tools.utils.HexByteUtil;

public class HexByteTranslator extends AbstractTranslator implements Translator {

    private String input;
    private byte[] output;


    public HexByteTranslator(){
        this.code = "hex-byte";
    }

    @Override
    public void doTranslate() throws Exception {
        this.output = HexByteUtil.hexStringToByte(this.input);
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof String;
        this.input = (String) input;
    }

    @Override
    public Object getOutput() {
        return this.output;
    }
}
