package com.snake.data.translate.function;

import com.snake.data.translate.AbstractTranslator;
import com.snake.data.translate.Translator;


public class StringTrimTranslator extends AbstractTranslator implements Translator {

    private String input;
    private String output;

    public StringTrimTranslator() {
        this.code = "string-trim";
    }

    @Override
    public void setInput(Object input) {
        assert input instanceof String;
        this.input = (String) input;
    }

    @Override
    public void doTranslate() {
        this.output = this.input.trim();
    }

    @Override
    public Object getOutput() {
        return this.output;
    }

}
