package me.snake.tools.protocol;


import me.snake.tools.Constants;
import me.snake.tools.config.Parameter;
import me.snake.tools.utils.BCDByteUtil;
import me.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

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
        this.byteLength = byteLength;
        this.value = value;
    }

    public static byte[] parseByteArray(String value, String byteType, int byteLength) throws UnsupportedEncodingException {
        if (byteType.equals(Parameter.byte_type_string)) {
            byte[] data = value.getBytes(Constants.CHARTSET_GBK);
            if (byteLength > 0) {
                byte[] bytes = new byte[byteLength];
                System.arraycopy(data, 0, bytes, 0, data.length > byteLength ? byteLength : data.length);
                return bytes;
            } else {
                return data;
            }
        } else if (byteType.equals(Parameter.byte_type_byte) && byteLength > 0) {
            byte[] bytes = new byte[byteLength];
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                bytes[i] = (byte) c;
            }
            return bytes;
            //todo special for id and validCode at yun.sanmeditech.com:30007
        } else if (byteType.equals(Parameter.byte_type_bcd) && byteLength > 0) {
            return BCDByteUtil.number2bcd(Long.parseLong(value), (byte) byteLength);
        }
        return null;
    }

    public static byte[] long2byte(long value, String byteType, int byteLength) throws UnsupportedEncodingException {
        return BCDByteUtil.number2bcd(value, (byte) byteLength);
    }

    public byte[] encode() throws UnsupportedEncodingException {
        if (javaType.equals(Parameter.java_type_string)) {
            String value = (String) this.value;
            bytes = parseByteArray(value, byteType, byteLength);
        } else if (javaType.equals(Parameter.java_type_date)) {
            String value = (String) this.value;
            bytes = parseByteArray(value.replaceAll("-", ""), Parameter.byte_type_bcd, byteLength);
        } else if (javaType.equals(Parameter.java_type_datetime)) {
            String value = (String) this.value;
            bytes = parseByteArray(value.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", ""), Parameter.byte_type_bcd, byteLength);
        } else if (javaType.equals(Parameter.java_type_long)) {
            long value = ((Long) this.value).longValue();
            bytes = long2byte(value, byteType, byteLength);
        } else if (javaType.equals(Parameter.java_type_integer)) {
            int value = ((Integer) this.value).intValue();
            bytes = ByteUtil.int2byte(value);
        } else if (javaType.equals(Parameter.java_type_double)) {
            double value = (long) ((Double) this.value).doubleValue();
            bytes = long2byte((long) (value * 100), byteType, byteLength);
        } else if (javaType.equals(Parameter.java_type_boolean)) {
            boolean value = ((Boolean) this.value).booleanValue();
            bytes = new byte[1];
            bytes[0] = (byte) (value ? 1 : 0);
        }
        return bytes;
    }

    public boolean decode() throws UnsupportedEncodingException {
        if (null != bytes && bytes.length > 0) {
            if (javaType.equals(Parameter.java_type_string)) {
                if (byteType.equals(Parameter.byte_type_bcd)) {
                    value = String.format("%l", BCDByteUtil.bcd2long(bytes));
                } else if (byteType.equals(Parameter.byte_type_string)) {
                    value = new String(bytes, Constants.CHARTSET_CODE_GBK);
                } else if (byteType.equals(Parameter.byte_type_byte)) {
                    //todo special for id and validCode at yun.sanmeditech.com:30007
                    StringBuffer sb = new StringBuffer();
                    for (byte b : bytes) {
                        sb.append(b);
                    }
                    value = sb.toString();
                }
            } else if (javaType.equals(Parameter.java_type_integer)) {
                value = ByteUtil.byte2int(bytes);
            } else if (javaType.equals(Parameter.java_type_long)) {
                value = ByteUtil.byte2long(bytes);
            } else if (javaType.equals(Parameter.java_type_double)) {
                value = ((double) ByteUtil.byte2long(bytes)) / 100;
            } else if (javaType.equals(Parameter.java_type_date)) {
                value = BCDByteUtil.hexString(bytes);
            } else if (javaType.equals(Parameter.java_type_datetime)) {
                value = BCDByteUtil.hexString(bytes);
            }
            return true;
        }
        return false;
    }

}
