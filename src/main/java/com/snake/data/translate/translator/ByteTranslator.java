package com.snake.data.translate.translator;


import com.snake.data.config.ParameterType;
import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
import com.snake.tools.Constants;
import com.snake.tools.utils.BCDByteUtil;
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

    @Translate("length-byte")
    public byte[] fullLength(byte[] value) throws Exception {
        assert value.length > Constants.LENGTH_BYTE_LENGTH;
        byte[] lengthBytes = new byte[Constants.LENGTH_BYTE_LENGTH];
        System.arraycopy(value, 0, lengthBytes, 0, Constants.LENGTH_BYTE_LENGTH);
        int length = ByteUtil.byte2int(lengthBytes);
        assert value.length >= Constants.LENGTH_BYTE_LENGTH + length;
        byte[] resultBytes = new byte[length];
        System.arraycopy(value, Constants.LENGTH_BYTE_LENGTH, resultBytes, 0, resultBytes.length);
        return resultBytes;
    }

    @Translate("bcd-long")
    public long bcd2long(byte[] value){
        return BCDByteUtil.bcd2long(value);
    }

}
