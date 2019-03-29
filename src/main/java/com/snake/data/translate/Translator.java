package com.snake.data.translate;

import com.snake.data.config.Type;

public interface Translator {

    String getCode();
    void setConfig(Type config);
    void debug(boolean debug);//todo should been move
    void setInput(Object input);
    void translate() throws Exception;
    Object getOutput();

}
