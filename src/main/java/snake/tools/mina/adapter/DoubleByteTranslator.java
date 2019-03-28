package snake.tools.mina.adapter;

import snake.tools.utils.ByteUtil;

import java.math.BigDecimal;

/**
 * Created by HP on 2018/5/24.
 */
public class DoubleByteTranslator extends AbstractByteTranslator<Double> {

    private static final int length = 8;
    private double divisor = 10;

    @Override
    public String getType() {
        return "double";
    }

    @Override
    public void setDecimal(int decimal) {
        assert decimal >= 0 && decimal <= 10;
        this.divisor = Math.pow(10, decimal);
    }

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        if (value instanceof BigDecimal) {
            return ByteUtil.long2byte((long) (((BigDecimal) value).doubleValue() * divisor));
        }
        return ByteUtil.long2byte((long) (((Double) value) * divisor));
    }

    @Override
    public Double decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return (double) ByteUtil.byte2long(bytes) / divisor;
        }
        return null;
    }

}
