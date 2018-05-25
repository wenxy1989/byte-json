package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class LongByteTranslator extends AbstractByteTranslator<Long> {

    private static final int length = 8;

    @Override
    public String getType() {
        return "long";
    }

    @Override
    public byte[] encode(Object value) {
        if (null != value) {
            return ByteUtil.long2byte((Long)value);
        }
        return null;
    }

    @Override
    public Long decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return ByteUtil.byte2long(bytes);
        }
        return null;
    }

}
