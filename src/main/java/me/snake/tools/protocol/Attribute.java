package me.snake.tools.protocol;


import com.sanmeditech.utils.MD5Util;
import me.snake.tools.Constants;
import me.snake.tools.adapter.ByteTranslatorManager;
import me.snake.tools.config.Parameter;
import me.snake.tools.utils.BCDByteUtil;
import me.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Attribute {

    private String code;
    private String type;
    private int length;
    private int decimal;

    private Object value;

    private byte[] bytes;

    public Attribute(String code, String type, int length, int decimal) {
        this.code = code;
        this.type = type;
        this.length = length < 0 ? 0 : length;
        this.decimal = decimal < 0 ? 0 : decimal;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{code:%s,type: %s,length: %d,decimal:%d }", code, type, value);
    }

    public byte[] encode() {
        return ByteTranslatorManager.encode(value, type, length, decimal);
    }

    public boolean decode() {
        if (null != bytes && bytes.length > 0) {
            ByteTranslatorManager.decode(bytes, type, length, decimal);
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public int getLength() {
        return length;
    }
}
