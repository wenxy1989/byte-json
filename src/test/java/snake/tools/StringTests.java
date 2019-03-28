package snake.tools;

import snake.tools.utils.BCDByteUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by HP on 2018/1/9.
 */
public class StringTests {

    @Test
    public void newStringTest() throws UnsupportedEncodingException {
        byte[] bytes = BCDByteUtil.hexString2byte("000D7B22657272636F6465223A307D");
        System.out.println(new String(bytes, Constants.CHARSET_CODE_GBK));
    }

}
