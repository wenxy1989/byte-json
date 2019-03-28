package snake.tools.adapter;

import snake.tools.utils.ByteUtil;

import java.math.BigDecimal;

/**
 * Created by HP on 2018/5/24.
 */
public class FloatByteTranslator extends AbstractByteTranslator<Float> {

    private static final int length = 4;

    private float divisor = 5;

    @Override
    public String getType() {
        return "float";
    }

    @Override
    public void setDecimal(int decimal) {
        assert decimal >= 0 && decimal <= 10;
        this.divisor = (float)Math.pow(10, decimal);
    }

    @Override
    public byte[] encode(Object value) {
        assert value != null;
        if(value instanceof BigDecimal){
            return ByteUtil.int2byte((int) (((BigDecimal)value).doubleValue() * divisor));
        }else if(value instanceof Double){
            return ByteUtil.int2byte((int) (((Double) value) * divisor));
        }else if(value instanceof Integer){
            return ByteUtil.int2byte((int) (((Integer) value) * divisor));
        }
        return ByteUtil.int2byte((int) (((Float) value) * divisor));
    }

    @Override
    public Float decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return (float) ByteUtil.byte2long(bytes) / divisor;
        }
        return null;
    }

}
