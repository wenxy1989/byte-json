package me.snake.tools.adapter;

import me.snake.tools.utils.ByteUtil;

/**
 * Created by HP on 2018/5/24.
 */
public class CharByteTranslator extends AbstractByteTranslator<Character> {

    private static final int length = 2;

    @Override
    public String getType() {
        return "char";
    }

    @Override
    public byte[] encode(Object value) {
        assert (null != value);
        if(value instanceof String){
            return ByteUtil.short2byte((short) ((String) value).charAt(0));
        }
        return ByteUtil.short2byte((short) ((Character) value).charValue());
    }

    @Override
    public Character decode(byte[] bytes) {
        assert (null != bytes && bytes.length == length);
        return (char) ByteUtil.byte2short(bytes);
    }

}
