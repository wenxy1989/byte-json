package snake.tools.mina.adapter;

/**
 * Created by HP on 2018/5/24.
 */
public class ByteTranslator extends AbstractByteTranslator<Byte> {

    private static final int length = 1;

    @Override
    public String getType() {
        return "byte";
    }

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        if(value instanceof Integer){
            return new byte[]{((Integer) value).byteValue()};
        }else if(value instanceof String){
            return new byte[]{(byte)((String) value).charAt(0)};
        }
        return new byte[]{(Byte) value};
    }

    @Override
    public Byte decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return bytes[0];
    }

}
