package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class ShortByteTranslator extends AbstractByteTranslator<Short> {

    private static final int length = 2;

    @Override
    public byte[] encode(Object value) {
        if (null != value) {
            return ByteUtil.int2byte((Short)value);
        }
        return null;
    }

    @Override
    public Short decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return ByteUtil.byte2short(bytes);
        }
        return null;
    }

}
