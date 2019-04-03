package com.snake.data.json;

public interface ArrayProvider {

    boolean isObject(int index);

    Object getValue(int index);

    int size();
}
