package com.snake.data.translate;

import com.snake.data.config.Type;

import java.util.Arrays;

public abstract class AbstractTranslator implements Translator {

    private boolean debug = false;
    protected String code;
    protected Type config;

    public AbstractTranslator(){}

    public AbstractTranslator(Type config){
        this.config = config;
    }

    @Override
    public void setConfig(Type config) {
        this.config = config;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void debug(boolean debug) {
        this.debug = debug;
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
        if(this.debug) {
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
