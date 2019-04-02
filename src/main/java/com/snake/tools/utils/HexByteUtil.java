package com.snake.tools.utils;

public class HexByteUtil {

    // Integer.toHexString(1000);
    private static byte charToByte(char c) {
        //return (byte) "0123456789ABCDEF".indexOf(c);
        return (byte) "0123456789abcdef".indexOf(c);
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (charToByte(achar[pos]) << 4 | charToByte(achar[pos + 1]));
        }
        return result;
    }

    /*输入16进制byte[]输出16进制字符串*/
    public static String byteArrayToHexString(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String hexString(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    public static String hexString(byte[] b) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < b.length; i++) {
            sb.append(hexString(b[i]).toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] hex2byte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (char2byte(chars[pos]) << 4 | char2byte(chars[pos + 1]));
        }
        return result;
    }

    private static byte char2byte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}
