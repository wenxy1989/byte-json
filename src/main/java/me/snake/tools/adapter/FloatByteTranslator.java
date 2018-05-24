package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class FloatByteTranslator extends AbstractByteTranslator<Float> {

    private static final int length = 4;

    private float divisor = 5;

    @Override
    public void setDecimal(int decimal) {
        assert decimal >= 0 && decimal <= 10;
        this.divisor = (float)Math.pow(10, decimal);
    }

    @Override
    public byte[] encode(Object value) {
        if (null != value) {
            return ByteUtil.long2byte((long) (((Float)value) * divisor));
        }
        return null;
    }

    @Override
    public Float decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return (float) ByteUtil.byte2long(bytes) / divisor;
        }
        return null;
    }

}
