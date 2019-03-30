package com.snake.data.translate;

import com.snake.data.config.Type;

import java.util.Arrays;


public class TranslateChain extends AbstractTranslator implements Translator {

    private Object input;
    private Object output;
    private Translator[] translators;

    public TranslateChain(Config config,String... translateChain) {
        super(config);
        this.code = Arrays.toString(translateChain);
    }

    public Translator[] getTranslators() {
        return translators;
    }

    public void setTranslators(Translator[] translators) {
        this.translators = translators;
    }

    @Override
    public void setInput(Object input) {
        assert null != input;
        this.input = input;
    }

    @Override
    public void doTranslate() throws Exception{
        Object temp =this.input;
        for(Translator translator: this.translators){
            translator.setInput(temp);
            translator.translate();
            temp = translator.getOutput();
        }
        this.output = temp;
    }

    @Override
    public Object getOutput() {
        return output;
    }

}
