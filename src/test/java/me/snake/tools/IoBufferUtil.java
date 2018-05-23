package me.snake.tools;

import me.snake.tools.config.Parameter;
import me.snake.tools.protocol.Attribute;
import org.apache.mina.core.buffer.IoBuffer;
import org.junit.Test;

import java.nio.charset.CharacterCodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenxy on 2017/12/31.
 */
public class IoBufferUtil {

    public static String parseBCD(IoBuffer io, Integer length) {
        String value = io.getHexDump(length).replaceAll("\\.", "").replaceAll(" ", "");
        io.position(io.position() + length);
        return value;
    }

    public static Map<String, Object> decodeByteArray(byte[] bytes, List<Attribute> attributes) throws CharacterCodingException {
        Map<String, Object> map = null;
        if (null != attributes) {
            IoBuffer io = IoBuffer.allocate(bytes.length);
            io.put(bytes);
            io.position(0);
            map = new HashMap<String, Object>();
            for (int i = 0; i < attributes.size(); i++) {
                Attribute attribute = attributes.get(i);
                Object value = decode(io,attribute.getJavaType(),attribute.getByteType(),attribute.getByteLength());
//                map.put(attribute.getCode(), value);
                //todo
            }
        }
        return map;
    }

    public static Object decode(IoBuffer io, Parameter.JavaType javaType, Parameter.ByteType byteType, Integer byteLength) throws CharacterCodingException {
        Object value = null;
        if (Parameter.JavaType.type_string.equals(javaType)) {
            if (Parameter.byte_type_bcd.equals(byteType) && byteLength > 0) {
                value = parseBCD(io, byteLength);
            } else if (Parameter.byte_type_string.equals(byteType)) {
                if (byteLength == 0) {
                    value = io.getString(Constants.CHARTSET_GBK_DECODER);
                } else {
                    value = io.getString(byteLength, Constants.CHARTSET_GBK_DECODER);
                }
            }
        } else if (Parameter.JavaType.type_long.equals(javaType)) {
            if(Parameter.byte_type_bcd.equals(byteType)){
                value = Long.parseLong(parseBCD(io, byteLength));
            }else{
                value = io.getUnsignedInt();
            }
        } else if (Parameter.JavaType.type_integer.equals(javaType)) {
            if(Parameter.byte_type_bcd.equals(byteType)){
                value = Integer.parseInt(parseBCD(io, byteLength));
            }else{
                value = io.getInt();
            }
        } else if (Parameter.JavaType.type_short.equals(javaType)) {
            if(Parameter.byte_type_bcd.equals(byteType)){
                value = Short.parseShort(parseBCD(io, byteLength));
            }else{
                value = io.get();
            }
        } else if (Parameter.JavaType.type_boolean.equals(javaType)) {
            value = io.get() == 1 ? true : false;
        }
        return value;
    }


    @Test
    public void IoBufferGetStringTest() throws CharacterCodingException {
        IoBuffer io = IoBuffer.allocate(10);
        io.getString(Constants.CHARTSET_GBK_DECODER);
    }

}
