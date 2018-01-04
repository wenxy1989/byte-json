package me.snake.mina;

import me.snake.mina.utils.BCDByteUtil;
import org.junit.Test;

/**
 * Created by wenxy on 2018/1/1.
 */
public class ByteUtilTests {

    @Test
    public void bcd2intTest(){
        int num = BCDByteUtil.bcd2Int(new byte[]{0x19, 0x01});
        assert num == 1901;
    }

}
