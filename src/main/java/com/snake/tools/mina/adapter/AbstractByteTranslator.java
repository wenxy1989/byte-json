package com.snake.tools.mina.adapter;

/**
 * Created by HP on 2018/5/24.
 */
public abstract class AbstractByteTranslator<T> {

    public void setLength(int length){}

    public void setDecimal(int decimal){}

    public abstract String getType();

    public abstract byte[] encode(Object value);

    public abstract T decode(byte[] bytes);

}
