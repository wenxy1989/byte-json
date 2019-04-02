package com.snake.data.json;

import com.snake.data.config.Parameter;

public interface ValueProvider {

    void setParameter(Parameter parameter);

    Object getValue(int index);

    Object getValue();

    boolean isObject();

    boolean isArray();

    int size();

    String valueString();
}
