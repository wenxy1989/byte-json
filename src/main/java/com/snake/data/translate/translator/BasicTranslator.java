package com.snake.data.translate.translator;


import com.snake.data.config.ParameterType;
import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
import com.snake.tools.Constants;
import com.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

@Translator
public class BasicTranslator {

    @Translate("bool-byte")
    public byte[] bool2byte(boolean value) {
        return new byte[]{(byte) (value ? 1 : 0)};
    }

    @Translate("byte-byte")
    public byte[] byte2byte(byte value) {
        return new byte[]{value};
    }

    @Translate("char-byte")
    public byte[] char2byte(char value) {
        return new byte[]{
                (byte) ((value & 0xFF00) >> 8),
                (byte) (value & 0xFF)
        };
    }

    @Translate("short-byte")
    public byte[] short2byte(short value) {
        return ByteUtil.short2byte(value);
    }

    @Translate("int-byte")
    public byte[] int2byte(int value) {
        return ByteUtil.int2byte(value);
    }

    @Translate("float-byte")
    public byte[] float2byte(float value, ParameterType type) {
        return ByteUtil.int2byte((int)(value*Math.pow(10,type.getDecimal())));
    }

    @Translate("long-byte")
    public byte[] long2byte(long value) {
        return ByteUtil.long2byte(value);
    }

    @Translate("double-byte")
    public byte[] double2byte(double value, ParameterType type) {
        return ByteUtil.long2byte((long) (value*Math.pow(10,type.getDecimal())));
    }

    @Translate("string-byte")
    public byte[] string2byte(String value) throws UnsupportedEncodingException {
        return value.getBytes(Constants.CHARSET_CODE_GBK);
    }

    @Translate("byte-bool")
    public boolean byte2bool(byte[] value) {
        return value[0] == 1;
    }

    @Translate("byte-char")
    public char byte2char(byte[] value) {
        return new String(value).charAt(0);
    }

    @Translate("byte-short")
    public short byte2short(byte[] value) {
        return ByteUtil.byte2short(value);
    }

    @Translate("byte-int")
    public int byte2int(byte[] value) {
        return ByteUtil.byte2int(value);
    }

    @Translate("byte-float")
    public float byte2float(byte[] value, ParameterType type) {
        return (float) ((float) ByteUtil.byte2int(value)/Math.pow(10,type.getDecimal()));
    }

    @Translate("byte-long")
    public long byte2long(byte[] value) {
        return ByteUtil.byte2long(value);
    }

    @Translate("byte-double")
    public double byte2double(byte[] value, ParameterType type) {
        return ByteUtil.byte2long(value)/Math.pow(10,type.getDecimal());
    }

    @Translate("byte-string")
    public String byte2string(byte[] value) throws UnsupportedEncodingException {
        return new String(value,Constants.CHARSET_CODE_GBK);
    }
}
