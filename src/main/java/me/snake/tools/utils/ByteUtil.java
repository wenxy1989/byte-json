package me.snake.tools.utils;

import me.snake.tools.Constants;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Title:整型与长度为4的字节数组的互换
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author jackie
 */
public class ByteUtil {

    public static byte[] concat(byte[]... bytes) {
        int length = 0;
        for (int i = 0; i < bytes.length; i++) {
            length += bytes[i].length;
        }
        byte[] byteArray = new byte[length];
        for (int i = 0, offset = 0; i < bytes.length; offset += bytes[i].length, i++) {
            System.arraycopy(bytes[i], 0, byteArray, offset, bytes[i].length);
        }
        return byteArray;
    }

    public static byte[] concat(byte[] bytes, byte... data) {
        int length = bytes.length + data.length;
        byte[] byteArray = new byte[length];
        System.arraycopy(bytes, 0, byteArray, 0, bytes.length);
        for (int i = 0; i < data.length; i++) {
            byteArray[bytes.length + i] = data[i];
        }
        return byteArray;
    }

    public static byte[] number2byte(long number, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < bytes.length; i++) {
            int move = (length - 1 - i);
            bytes[i] = (byte) (0xFF & (number >> (8 * move)));
        }
        return bytes;
    }

    public static long byte2number(byte[] bytes) {
        int longLength = 8;
        byte[] longFace = new byte[longLength];
        if (bytes[0] < 0) {
            for (int i = 0; i < longFace.length; i++) {
                longFace[i] = (byte) 0xFF;
            }
        }
        System.arraycopy(bytes, 0, longFace, longLength - bytes.length, bytes.length);
        long number = 0;
        for (int i = 0; i < longFace.length; i++) {
            int move = longFace.length - 1 - i;
            long temp = (((long) (0xFF & longFace[i])) << (8 * move));
            number += temp;
        }
        return number;
    }

    public static byte[] short2byte(short value) {
        assert (value >= Short.MIN_VALUE & value <= Short.MAX_VALUE);
        return number2byte(value,2);
    }

    public static byte[] int2byte(int value) {
        assert (value > Integer.MIN_VALUE & value <= Integer.MAX_VALUE);
        return number2byte(value,4);
    }

    public static byte[] long2byte(long value) {
        assert (value >= Long.MIN_VALUE & value <= Long.MAX_VALUE);
        return number2byte(value,8);
    }

    public static short byte2short(byte[] b) {
        assert null != b;
        return (short)byte2number(b);
    }

    public static int byte2int(byte[] b) {
        assert null != b;
        return (int)byte2number(b);
    }

    public static long byte2long(byte[] b) {
        assert null != b;
        return byte2number(b);
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

    public static String byte2string(byte[] bytes) {
        if (null != bytes && bytes.length > 0) {
            try {
                return new String(bytes, Constants.CHARTSET_CODE_GBK);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String fixedByte2string(byte[] bytes) {
        if (null != bytes && bytes.length > 0) {
            int length = 0;
            for (; length < bytes.length; length++) {
                if (bytes[length] == 0) {
                    break;
                }
            }
            byte[] validBytes = new byte[length];
            System.arraycopy(bytes, 0, validBytes, 0, length);
            return byte2string(validBytes);
        }
        return null;
    }

}