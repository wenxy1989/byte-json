package com.snake.data.translate.function;

import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;

public class ByteFullLengthTranslator extends AbstractTranslator implements Translator {

    private byte[] input;
    private byte[] output;

    public ByteFullLengthTranslator() {
        this.code = "byte-full-length";
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof byte[];
        this.input = (byte[]) input;
    }

    @Override
    public void doTranslate() throws Exception {
        this.output = new byte[this.config.getType().getByteLength()];
        System.arraycopy(this.input, 0, this.output, this.config.getType().getByteLength() - this.input.length, this.input.length);
    }

    @Override
    public Object getOutput() {
        return this.output;
    }
}
