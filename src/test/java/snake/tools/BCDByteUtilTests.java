package snake.tools;

import snake.tools.utils.BCDByteUtil;
import org.junit.Test;

/**
 * Created by wenxy on 2018/1/1.
 */
public class BCDByteUtilTests {

    public void show(Object object) {
        System.out.println(object + "");
    }

    @Test
    public void bcd2shortTest() {
        short source = 1090;
        byte[] bcd = BCDByteUtil.short2bcd(source);
        String hexString = BCDByteUtil.hexString(bcd);
        show(hexString);
        short target = BCDByteUtil.bcd2short(bcd);
        show(target);
        assert source == target;
    }

    @Test
    public void bcd2intTest() {
        int num = BCDByteUtil.bcd2Int(new byte[]{0x19, 0x01});
        assert num == 1901;
    }

    @Test
    public void bcd2longTest() {
        long num = BCDByteUtil.bcd2long(new byte[]{0x19, 0x01});
        assert num == 1901;
    }

}
