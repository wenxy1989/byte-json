package me.snake.tools.adapter;

/**
 * Created by HP on 2018/5/24.
 */
public class ByteTranslator extends AbstractByteTranslator<Byte> {

    private static final int length = 1;

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        return new byte[]{(Byte) value};
    }

    @Override
    public Byte decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return bytes[0];
    }

}
