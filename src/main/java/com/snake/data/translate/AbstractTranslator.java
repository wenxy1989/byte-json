package com.snake.data.translate;

import com.snake.data.config.Type;

import java.util.Arrays;

public abstract class AbstractTranslator implements Translator {

    protected String code;
    protected Config config;

    public AbstractTranslator(){}

    public AbstractTranslator(Config config){
        this.config = config;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void prepareTranslate(){

    }

    @Override
    public void translate() throws Exception {
        this.prepareTranslate();
        this.doTranslate();
        this.afterTranslate();
    }

    public abstract void doTranslate() throws Exception;

    public void afterTranslate(){
        if(this.config.isDebug()) {
            this.printOutput();
        }
    }

    public void printOutput(){
        String print = null;
        if(this.getOutput() instanceof Object[]){
            print = Arrays.toString((Object[]) this.getOutput());
        }else if(this.getOutput() instanceof byte[]){
            print = Arrays.toString((byte[])this.getOutput());
        }else{
            print = this.getOutput().toString();
        }
        System.out.println(String.format("%s translate output %s",this.code,print));
    }

}
