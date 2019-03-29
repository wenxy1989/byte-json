package com.sanmeditech.adapter;

import com.snake.tools.mina.adapter.AbstractByteTranslator;

/**
 * Created by HP on 2018/5/24.
 */
public class ByteBCDTranslator extends AbstractByteTranslator<Long> {

    protected int length = 6;

    @Override
    public String getType() {
        return "bcd-byte";
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public byte[] encode(Object value){
        assert null != value;
        String valueString = null;
        String format = "%(0"+length+"d";
        if(value instanceof Long){
            valueString = String.format(format,(Long)value);
        }else if(value instanceof Integer){
            valueString = String.format(format,(Integer)value);
        }else {
            valueString = (String)value;
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            char c = valueString.charAt(i);
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
