package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class DoubleByteTranslator extends AbstractByteTranslator<Double> {

    private static final int length = 8;
    private double divisor = 10;

    @Override
    public void setDecimal(int decimal) {
        assert decimal >= 0 && decimal <= 10;
        this.divisor = Math.pow(10, decimal);
    }

    @Override
    public byte[] encode(Object value) {
        if (null != value) {
            return ByteUtil.long2byte((long) (((Double) value) * divisor));
        }
        return null;
    }

    @Override
    public Double decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return (double) ByteUtil.byte2long(bytes) / divisor;
        }
        return null;
    }

}
