package snake.tools.mina.adapter;

/**
 * Created by HP on 2018/5/24.
 */
public class BooleanTranslator extends AbstractByteTranslator<Boolean> {

    private static final int length = 1;

    @Override
    public String getType() {
        return "boolean";
    }

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        return new byte[]{(byte) (((Boolean)value).booleanValue() ? 1 : 0)};
    }

    @Override
    public Boolean decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return (bytes[0] == 1);
    }

}
