package com.snake.data.translate.function;

import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;

public class FIllBlankTranslator extends AbstractTranslator implements Translator {

    private byte[] input;
    private byte[] output;

    public FIllBlankTranslator() {
        this.code = "byte-fill-blank";
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof byte[];
        this.input = (byte[]) input;
    }

    @Override
    public void doTranslate() throws Exception {
        this.output = new byte[this.config.getByteLength()];
        System.arraycopy(this.input, 0, this.output, this.config.getByteLength() - this.input.length, this.input.length);
    }

    @Override
    public Object getOutput() {
        return this.output;
    }
}
