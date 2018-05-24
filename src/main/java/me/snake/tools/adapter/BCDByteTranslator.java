package me.snake.tools.adapter;

import me.snake.tools.utils.BCDByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class BCDByteTranslator extends AbstractByteTranslator<Long> {

    private int length = 8;

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public byte[] encode(Object value) {
        assert null != value;
        return BCDByteUtil.number2bcd((Long)value, length);
    }

    @Override
    public Long decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return BCDByteUtil.bcd2long(bytes);
    }

}
