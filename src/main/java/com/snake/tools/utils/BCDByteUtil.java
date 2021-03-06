package com.snake.tools.utils;

/**
 * Created by wenxy on 2018/1/1.
 */
public class BCDByteUtil {

    public static byte byte2bcd(byte number) {
        assert number >= 0 && number <= 99;
        byte low = (byte) (number % 10);
        byte high = (byte) ((number / 10) % 10);
        return (byte) ((high << 4) | low);
    }

    public static byte[] number2bcd(long number, int length) {
        assert length > 0 && length <= 20;
        byte[] bytes = new byte[length];
        long factor = number;
        byte rem = (byte) (factor % 100);
        for (int i = 0; i < length && factor > 0; i++) {
            bytes[length - 1 - i] = byte2bcd(rem);
            factor = factor / 100;
            rem = (byte) (factor % 100);
        }
        return bytes;
    }

    public static byte[] long2bcd(long number) {
        return number2bcd(number, (byte) 8);
    }

    public static byte[] int2bcd(int number) {
        return number2bcd(number, (byte) 4);
    }

    public static byte[] short2bcd(short number) {
        return number2bcd(number, (byte) 2);
    }

    public static long bcd2long(byte[] bcd) {
        assert null != bcd && bcd.length > 0;
        long number = 0;
        for (int i = 0; i < bcd.length; i++) {
            number = (number * 100) + (bcd[i] >> 4) * 10 + (0x0F & bcd[i]);
        }
        return number;
    }

    public static int bcd2Int(byte[] bcd) {
        assert null != bcd && bcd.length <= 4;
        int number = 0;
        for (int i = 0; i < bcd.length; i++) {
            number = (number * 100) + (bcd[i] >> 4) * 10 + (0x0F & bcd[i]);
        }
        return number;
    }

    public static byte bcd2byte(byte bcd) {
        return (byte) ((0x0F & (bcd >> 4)) * 10 + (0x0F & bcd));
    }

    public static short bcd2short(byte[] bcd) {
        assert null != bcd && bcd.length <= 2;
        short number = 0;
        for (int i = 0; i < bcd.length; i++) {
            byte bcdNumber = bcd2byte(bcd[i]);
            number = (short) ((number * 100) + bcdNumber);
        }
        return number;
    }
}
