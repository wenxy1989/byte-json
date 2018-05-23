package me.snake.tools.adapter;

import com.alibaba.fastjson.JSONObject;
import com.sanmeditech.utils.MD5Util;
import me.snake.tools.Constants;
import me.snake.tools.config.Command;
import me.snake.tools.config.Parameter;
import me.snake.tools.protocol.Attribute;
import me.snake.tools.protocol.Content;
import me.snake.tools.utils.BCDByteUtil;
import me.snake.tools.utils.ByteUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class AttributeByteAdapter {

    private Object value;
    private byte[] bytes;
    private Attribute attribute;

    public AttributeByteAdapter(Attribute attribute) {
        this.attribute = attribute;
    }

    public byte[] encode(Object value, Parameter.JavaType javaType, Parameter.ByteType byteType, int length) {
        if (javaType == Parameter.JavaType.type_string) {
            return encode((String) value, byteType, length);
        } else if (javaType == Parameter.JavaType.type_double) {
            Double d = (Double) value;
        } else if (javaType == Parameter.JavaType.type_float) {
            Float f = (Float) value;
        } else if (javaType == Parameter.JavaType.type_long) {
            Long l = (Long) value;
        } else if (javaType == Parameter.JavaType.type_integer) {
            Integer i = (Integer) value;
        } else if (javaType == Parameter.JavaType.type_short) {
            Short s = (Short) value;
            return ByteUtil.short2byte(s);
        } else if (javaType == Parameter.JavaType.type_char) {
            char c = (Character) value;
            return ByteUtil.short2byte((short) c);//todo
        } else if (javaType == Parameter.JavaType.type_byte) {
            byte b = (Byte) value;
            return new byte[]{b};
        } else if (javaType == Parameter.JavaType.type_boolean) {
            boolean b = (Boolean) value;
            return new byte[]{(byte) (b ? 0x01 : 0x00)};
        }
        return null;
    }


    public static byte[] encode(String value, Parameter.ByteType byteType, int byteLength) {
        if (Parameter.ByteType.type_string.equals(byteType)) {
            return string2byte(value, byteLength);
        } else if (Parameter.ByteType.type_byte.equals(byteType) && byteLength > 0) {
            byte[] bytes = new byte[byteLength];
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                bytes[i] = (byte) Integer.parseInt(String.valueOf(c));
            }
            return bytes;
            //todo special for id and validCode at yun.sanmeditech.com:30007
        } else if (Parameter.ByteType.type_bcd.equals(byteType) && byteLength > 0) {
            return BCDByteUtil.number2bcd(Long.parseLong(value), (byte) byteLength);
        } else if (Parameter.ByteType.type_md5.equals(byteType) && byteLength > 0) {
            return string2byte(MD5Util.encode(value), byteLength);
        } else {
            return string2byte(value, byteLength);
        }
    }

    public static byte[] string2byte(String value, int length) {
        byte[] data = value.getBytes(Constants.CHARTSET_GBK);
        if (length > 0) {
            byte[] bytes = new byte[length];
            System.arraycopy(data, 0, bytes, 0, data.length > length ? length : data.length);
            return bytes;
        } else {
            return data;
        }
    }

    public Attribute decode(byte[] bytes) {
        return null;
    }

}
