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
        data = data.replaceAll(" ", "");
        byte[] bytes = BCDByteUtil.hexString2byte(data);
        Content content = Content.decode(bytes);
        if (null != content) {
            System.out.println(content.getBody().getJson());
        }
    }

}
