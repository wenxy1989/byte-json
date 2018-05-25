package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class StringByteTranslator extends AbstractByteTranslator<String> {

    private int length = 0;

    @Override
    public String getType() {
        return "string";
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public byte[] encode(Object value) {
        return ByteUtil.string2byte((String) value, length);
    }

    @Override
    public String decode(byte[] bytes) {
        return ByteUtil.fixedByte2string(bytes);
    }

}
