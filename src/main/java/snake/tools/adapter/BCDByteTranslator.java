package snake.tools.adapter;

import snake.tools.utils.BCDByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class BCDByteTranslator extends AbstractByteTranslator<Long> {

    private int length = 8;

    @Override
    public String getType() {
        return "bcd";
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public byte[] encode(Object value) {
        assert null != value;
        if(value instanceof Integer){
            return BCDByteUtil.number2bcd((Integer)value, length);
        }
        return BCDByteUtil.number2bcd((Long)value, length);
    }

    @Override
    public Long decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return BCDByteUtil.bcd2long(bytes);
    }

}
