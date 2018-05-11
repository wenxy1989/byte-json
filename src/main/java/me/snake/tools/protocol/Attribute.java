package me.snake.tools.protocol;


import com.sanmeditech.utils.MD5Util;
import me.snake.tools.Constants;
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
    private String javaType;
    private String byteType;
    private Integer byteLength;
    private Object value;

    private byte[] bytes;

    public String getCode() {
        return code;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getByteType() {
        return byteType;
    }

    public Integer getByteLength() {
        return byteLength;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return String.format("%s, %s : %s ", code, javaType, value);
    }

    public Attribute(String code, String javaType, String byteType, Integer byteLength, Object value) {
        this.code = code;
        this.javaType = javaType;
        this.byteType = byteType;
        this.byteLength = byteLength == null ? 0 : byteLength;
        this.value = value;
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

    public static byte[] parseByteArray(String value, String byteType, int byteLength) throws UnsupportedEncodingException {
        if (Parameter.byte_type_string.equals(byteType)) {
            return string2byte(value, byteLength);
        } else if (Parameter.byte_type_byte.equals(byteType) && byteLength > 0) {
            byte[] bytes = new byte[byteLength];
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                bytes[i] = (byte) Integer.parseInt(String.valueOf(c));
            }
            return bytes;
            //todo special for id and validCode at yun.sanmeditech.com:30007
        } else if (Parameter.byte_type_bcd.equals(byteType) && byteLength > 0) {
            return BCDByteUtil.number2bcd(Long.parseLong(value), (byte) byteLength);
        } else if (Parameter.byte_type_md5.equals(byteType) && byteLength > 0) {
            return string2byte(MD5Util.encode(value), byteLength);
        } else {
            return string2byte(value, byteLength);
        }
    }

    public static byte[] long2BCDbyte(long value, int byteLength) throws UnsupportedEncodingException {
        return BCDByteUtil.number2bcd(value, (byte) byteLength);
    }

    public byte[] encode() throws UnsupportedEncodingException {
//        if ("birth".equals(code)) {
//            System.out.println(" error is coming");
//        }
        if (Parameter.java_type_string.equals(javaType)) {
            String value = (String) this.value;
            bytes = parseByteArray(value, byteType, byteLength);
        } else if (Parameter.java_type_date.equals(javaType)) {
            String value = (String) this.value;
            value = value.replaceAll("-", "");
            bytes = parseByteArray(value, Parameter.byte_type_bcd, byteLength);
        } else if (Parameter.java_type_datetime.equals(javaType)) {
            String value = (String) this.value;
            value = value.replaceAll("-", "");
            value = value.replaceAll(":", "");
            value = value.replaceAll(" ", "");
            bytes = parseByteArray(value, Parameter.byte_type_bcd, byteLength);
        } else if (Parameter.java_type_double.equals(javaType)) {
            if(this.value instanceof Double){
                double value = ((Double) this.value).doubleValue();
                bytes = ByteUtil.long2byte((long) (value * 100));
            }else if(this.value instanceof BigDecimal){
                double value = ((BigDecimal) this.value).doubleValue();
                bytes = ByteUtil.long2byte((long) (value * 100));
            }
        } else if (Parameter.java_type_float.equals(javaType)) {
            float value = ((Float) this.value).floatValue();
            bytes = ByteUtil.int2byte((int) (value * 10));
        } else if (Parameter.java_type_long.equals(javaType)) {
            long value = ((Long) this.value).longValue();
            bytes = ByteUtil.long2byte(value);
        } else if (Parameter.java_type_integer.equals(javaType)) {
            int value = ((Integer) this.value).intValue();
            bytes = ByteUtil.int2byte(value);
        } else if (Parameter.java_type_short.equals(javaType)) {
            short value = ((Short) this.value).shortValue();
            bytes = ByteUtil.short2byte(value);
        } else if (Parameter.java_type_char.equals(javaType)) {
            char value = ((String) this.value).charAt(0);
            bytes = ByteUtil.short2byte((short) value);
        } else if (Parameter.java_type_byte.equals(javaType)) {
            byte value = ((Byte) this.value).byteValue();
            bytes = new byte[1];
            bytes[0] = value;
        } else if (Parameter.java_type_boolean.equals(javaType)) {
            boolean value = ((Boolean) this.value).booleanValue();
            bytes = new byte[1];
            bytes[0] = (byte) (value ? 1 : 0);
        } else {
            String value = (String) this.value;
            bytes = parseByteArray(value, byteType, byteLength);
        }
        return bytes;
    }

    public boolean decode() throws UnsupportedEncodingException {
        if (null != bytes && bytes.length > 0) {
            if (Parameter.java_type_string.equals(javaType)) {
                if (Parameter.byte_type_bcd.equals(byteType)) {
                    value = String.format("%d", BCDByteUtil.bcd2long(bytes));
                } else if (Parameter.byte_type_string.equals(byteType)) {
                    value = ByteUtil.fixedByte2string(bytes);
                } else if (Parameter.byte_type_byte.equals(byteType)) {
                    //todo special for id and validCode at yun.sanmeditech.com:30007
                    StringBuffer sb = new StringBuffer();
                    for (byte b : bytes) {
                        sb.append(b);
                    }
                    value = sb.toString();
                }
            } else if (Parameter.java_type_byte.equals(javaType)) {
                value = bytes[0];
            } else if (Parameter.java_type_short.equals(javaType)) {
                value = ByteUtil.byte2int(bytes);
            } else if (Parameter.java_type_integer.equals(javaType)) {
                value = ByteUtil.byte2int(bytes);
            } else if (Parameter.java_type_long.equals(javaType)) {
                value = ByteUtil.byte2long(bytes);
            } else if (Parameter.java_type_double.equals(javaType)) {
                value = ((double) ByteUtil.byte2long(bytes)) / 100;
            } else if (Parameter.java_type_date.equals(javaType)) {
                value = BCDByteUtil.hexString(bytes);
            } else if (Parameter.java_type_datetime.equals(javaType)) {
                value = BCDByteUtil.hexString(bytes);
            } else {
                value = ByteUtil.byte2string(bytes);
            }
            return true;
        }
        return false;
    }

}
