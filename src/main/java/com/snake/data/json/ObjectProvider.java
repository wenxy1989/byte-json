package com.snake.data.json;

import com.snake.data.config.Parameter;

public interface ObjectProvider {

    boolean isObject(Parameter parameter);

    boolean isArray(Parameter parameter);

    Object getValue(Parameter parameter);
}
