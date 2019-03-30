package com.snake.data.translate;

import com.snake.data.config.Type;

public interface Translator {

    String getCode();
    void setConfig(Config config);
    void setInput(Object input);
    void translate() throws Exception;
    Object getOutput();

}
