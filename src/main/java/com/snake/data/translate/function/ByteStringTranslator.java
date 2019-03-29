package com.snake.data.translate.function;

import com.snake.tools.Constants;
import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;

import java.io.UnsupportedEncodingException;

public class ByteStringTranslator extends AbstractTranslator implements Translator {

    private byte[] input;
    private String output;

    public ByteStringTranslator() {
        this.code = "byte-string";
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof byte[];
        this.input = (byte[]) input;
    }

    @Override
    public void doTranslate() throws UnsupportedEncodingException {
        this.output = new String(this.input, Constants.CHARSET_CODE_GBK);
    }

    @Override
    public Object getOutput() {
        return this.output;
    }

}
