package snake.tools.mina.adapter;

import snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class IntegerByteTranslator extends AbstractByteTranslator<Integer> {

    private static final int length = 4;

    @Override
    public String getType() {
        return "int";
    }

    @Override
    public byte[] encode(Object value) {
        if (null != value) {
            return ByteUtil.int2byte((Integer)value);
        }
        return null;
    }

    @Override
    public Integer decode(byte[] bytes) {
        if (null != bytes && bytes.length == length) {
            return ByteUtil.byte2int(bytes);
        }
        return null;
    }

}
