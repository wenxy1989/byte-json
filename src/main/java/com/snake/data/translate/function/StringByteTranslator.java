package com.snake.data.translate.function;

import com.snake.tools.Constants;
import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;

import java.io.UnsupportedEncodingException;

public class StringByteTranslator extends AbstractTranslator implements Translator {

    private String input;
    private byte[] output;

    public StringByteTranslator() {
        this.code = "string-byte";
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof String;
        this.input = (String) input;
    }

    public void doTranslate() throws UnsupportedEncodingException {
        this.output = this.input.getBytes(Constants.CHARSET_CODE_GBK);
    }

    @Override
    public Object getOutput() {
        return output;
    }
}
