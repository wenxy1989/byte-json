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

    public static byte[] short2byte(short value) {
        assert (value >= Short.MIN_VALUE & value <= Short.MAX_VALUE);
        return new byte[]{(byte) ((value >> 8) & 0xFF), (byte) (0x00ff & value)};
    }

    public static byte[] long2byte(long value) {
        assert (value >= Long.MIN_VALUE & value <= Long.MAX_VALUE);
        byte[] bytes = new byte[4];
        value = value & 0x00000000ffffffff;
        bytes[0] = (byte) (value >> (8 * 3));
        bytes[1] = (byte) (value >> (8 * 2));
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    public static byte[] int2byte(int value) {
        assert (value > Integer.MIN_VALUE & value <= Integer.MAX_VALUE);
        byte[] bytes = new byte[2];
        value = value & 0x0000ffff;
        bytes[0] = (byte) (value >> 8);
        bytes[1] = (byte) value;
        return bytes;
    }

    public static short byte2short(byte[] b) {
        short value = (short) (b[0] << 8);
        value += b[1];
        return value;
    }

    public static int byte2int(byte[] b) {
        int intValue = b[0] << 8;
        intValue += b[1];
        return intValue;
    }

    public static long byte2long(byte[] b) {
        long intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
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