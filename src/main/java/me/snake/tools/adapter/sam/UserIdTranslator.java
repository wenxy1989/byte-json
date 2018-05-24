package me.snake.tools.adapter.sam;

import me.snake.tools.adapter.AbstractByteTranslator;

/**
 * Created by HP on 2018/5/24.
 */
public class UserIdTranslator extends AbstractByteTranslator<Long> {

    private int length = 11;

    @Override
    public byte[] encode(Object value){
        assert null != value;
        String userIdString = String.format("%011d",value);
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            char c = userIdString.charAt(i);
            bytes[i] = (byte) Integer.parseInt(String.valueOf(c));
        }
        return bytes;
    }

    @Override
    public Long decode(byte[] bytes){
        assert null != bytes && bytes.length == length;
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(b);
        }
        return Long.parseLong(sb.toString());
    }

}
