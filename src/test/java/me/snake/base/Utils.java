package me.snake.base;

/**
 * Created by HP on 2018/3/28.
 */
public class Utils {

    public static String formatNumber(String number, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i >= length - number.length()) {
                sb.append(number.charAt(i - length + number.length()));
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }
}
