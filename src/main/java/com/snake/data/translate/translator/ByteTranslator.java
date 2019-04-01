package com.snake.data.translate.translator;


import com.snake.data.config.ParameterType;
import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;

@Translator("byte-")
public class ByteTranslator {

    @Translate("full-length")
    public byte[] fullLength(byte[] value, ParameterType type) throws Exception {
        int length = type.getByteLength();
        byte[] result = new byte[length];
        System.arraycopy(value, 0, result, length - value.length, value.length);
        return result;
    }
}
