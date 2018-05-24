package me.snake.tools;

import me.snake.tools.protocol.Content;
import me.snake.tools.utils.BCDByteUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by HP on 2018/1/8.
 */
public class ContentTests {

    @Test
    public void decodeTest() throws IOException {
        String data = "5B 04 00 00 00 02 08 00 2C 01 23 45 67 89 01 01 02 03 04 05 06 31 32 33 34 35 36 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 AA 5D";
        data = " 5B 04 00 00 00 02 08 00 2C 01 23 45 67 89 01 09 08 05 06 03 02 31 32 33 34 35 36 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 AE 5D";
        data = "5B040000000208002C0123456789010908050603023132333435360000000000000000000000000000000000000000000000000000AE5D";
        data = "5B040000009302000D7B22657272636F6465223A307DFC5D";
        data = "5B 04 00 00 00 04 01 00 62 77 65 6E 78 79 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 54 43 2D 32 30 31 38 30 31 30 34 00 00 00 00 00 00 00 00 00 43 2D 32 30 31 38 30 31 30 34 00 00 00 00 00 00 00 00 00 00 00 01 17 12 25 17 41 22 1F 5D";
        data = data.replaceAll(" ", "");
        byte[] bytes = BCDByteUtil.hexString2byte(data);
        Content content = Content.decode(bytes);
        if (null != content) {
            System.out.println(content.getBody().getJsonArray());
        }
    }

}
