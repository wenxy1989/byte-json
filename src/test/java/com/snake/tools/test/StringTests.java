package com.snake.tools.test;

import com.snake.tools.Constants;
import com.snake.tools.utils.BCDByteUtil;
import com.snake.tools.utils.HexByteUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by HP on 2018/1/9.
 */
public class StringTests {

    @Test
    public void newStringTest() throws UnsupportedEncodingException {
        byte[] bytes = HexByteUtil.hex2byte("000D7B22657272636F6465223A307D");
        System.out.println(new String(bytes, Constants.CHARSET_CODE_GBK));
    }

}
