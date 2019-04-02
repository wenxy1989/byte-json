package com.snake.tools.test;

import com.snake.tools.utils.BCDByteUtil;
import com.snake.tools.utils.ByteUtil;
import com.snake.tools.utils.HexByteUtil;
import org.junit.Test;

/**
 * Created by wenxy on 2018/1/1.
 */
public class ByteUtilTests {

    @Test
    public void bcd2intTest() {
        int num = BCDByteUtil.bcd2Int(new byte[]{0x19, 0x01});
        assert num == 1901;
    }

    private void swapNumberAndByte(long number, int length) {
        System.out.println(String.format("number is %d , %x", number, number));
        byte[] bytes = ByteUtil.number2byte(number, length);
        System.out.println(String.format("byte[] is %s", HexByteUtil.hexString(bytes)));
        long result = ByteUtil.byte2number(bytes);
        System.out.println(String.format("result is %d , %x", result, result));
        assert number == result;
    }

    @Test
    public void number2byteTest() {
        int number = 2222;
//        System.out.println((byte)number);
//        byte b = -82;
//        System.out.println(((int)0xFF & b));
//        System.out.println(String.format("long max is %d",Long.MAX_VALUE));
//        System.out.println(String.format("long max is %x",Long.MAX_VALUE));
//        System.out.println(String.format("long min is %d",Long.MIN_VALUE));
//        System.out.println(String.format("long min is %x", Long.MIN_VALUE));
        swapNumberAndByte(9223372036854775807L, 8);
        swapNumberAndByte(-9223372036854775807L,8);
        swapNumberAndByte(12312,4);
        swapNumberAndByte(-12312L, 4);
        swapNumberAndByte(233, 2);
        swapNumberAndByte(-233, 2);
        swapNumberAndByte(number, 4);
    }

}
