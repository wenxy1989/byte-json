package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class ShortByteTranslator extends AbstractByteTranslator<Short> {

    private static final int length = 2;

    @Override
    public String getType() {
        return "short";
    }

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        if (value instanceof Integer) {
            return ByteUtil.short2byte(((Integer) value).shortValue());
        }
        return ByteUtil.short2byte((Short) value);
    }

    @Override
    public Short decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return ByteUtil.byte2short(bytes);
        }
        return null;
    }

}
