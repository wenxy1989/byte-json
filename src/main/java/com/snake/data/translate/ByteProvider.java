package com.snake.data.translate;


import com.snake.data.config.Parameter;

public class ByteProvider {

    private byte[] bytes;
    private Parameter parameter;
    public ByteProvider(byte[] bytes){
        this.bytes = bytes;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public byte[] getBytes(){

        return null;
    }

    public byte[] getNextBytes(){

        return null;
    }

}
