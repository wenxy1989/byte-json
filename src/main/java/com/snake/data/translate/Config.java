package com.snake.data.translate;

import com.snake.data.config.Type;

public class Config {

    private boolean debug;
    private Type type;

    public Config(Type type,boolean debug){
        this.type = type;
        this.debug = debug;
    }

    public Config(Type type){
        this(type,false);
    }

    public boolean isDebug() {
        return debug;
    }

    public Type getType() {
        return type;
    }
}
