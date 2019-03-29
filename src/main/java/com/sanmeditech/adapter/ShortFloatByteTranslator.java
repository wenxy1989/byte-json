package com.sanmeditech.adapter;

import com.snake.tools.mina.adapter.AbstractByteTranslator;
import com.snake.tools.utils.ByteUtil;

import java.math.BigDecimal;

/**
 * Created by HP on 2018/5/24.
 */
public class ShortFloatByteTranslator extends AbstractByteTranslator<Float> {

    private static final int length = 2;

    private float divisor = 1;

    @Override
    public String getType() {
        return "float-short";
    }

    @Override
    public void setDecimal(int decimal) {
        assert decimal >= 0 && decimal <= 10;
        this.divisor = (float) Math.pow(10, decimal);
    }

    @Override
    public byte[] encode(Object value) {
        assert value != null;
        if (value instanceof BigDecimal) {
            return ByteUtil.short2byte((short) (((BigDecimal) value).doubleValue() * divisor));
        } else if (value instanceof Double) {
            return ByteUtil.short2byte((short) (((Double) value) * divisor));
        } else if (value instanceof Integer) {
            return ByteUtil.short2byte((short) (((Integer) value) * divisor));
        }
        return ByteUtil.short2byte((short) (((Float) value) * divisor));
    }

    @Override
    public Float decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return (float) ByteUtil.byte2short(bytes) / divisor;
    }

}
