package com.snake.data.translate;

import com.snake.data.config.ParameterType;

public class Config {

    private boolean debug;
    private ParameterType parameterType;

    public Config(boolean debug){
        this.debug = debug;
    }

    public Config(){
        this(false);
    }

    public boolean isDebug() {
        return debug;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }
}
