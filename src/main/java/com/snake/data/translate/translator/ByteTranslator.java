package com.snake.data.translate.translator;


import com.snake.data.config.ParameterType;
import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
import com.snake.tools.utils.ByteUtil;

@Translator("byte-")
public class ByteTranslator {

    @Translate("add-length")
    public byte[] addLength(byte[] value, ParameterType typ) throws Exception {
        byte[] lengthBytes = ByteUtil.int2byte(value.length);
        int totalLength = value.length + lengthBytes.length;
        byte[] result = new byte[totalLength];
        System.arraycopy(lengthBytes, 0, result, 0, lengthBytes.length);
        System.arraycopy(value, 0, result, lengthBytes.length, value.length);
        return result;
    }

    @Translate("full-length")
    public byte[] fullLength(byte[] value, ParameterType type) throws Exception {
        int length = type.getByteLength();
        byte[] result = new byte[length];
        System.arraycopy(value, 0, result, length - value.length, value.length);
        return result;
    }
}
