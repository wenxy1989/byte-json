package com.sanmeditech.collector.test;

import com.snake.base.SocketTool;
import com.snake.tools.utils.ByteUtil;
import com.snake.tools.utils.CodeParser;
import com.snake.tools.utils.HexByteUtil;
import org.junit.Test;

public class FixDataTests {

    public byte[] content(String head,String body){
        String data = String.format("%s%s",head,body);
        byte[] infoBytes = HexByteUtil.hexStringToByte(data);
        byte flag = CodeParser.check(infoBytes);
        byte[] decodeBytes = ByteUtil.concat(infoBytes, flag);
        byte[] encodeBytes = CodeParser.encode(decodeBytes);
        return ByteUtil.concat(new byte[]{0x5B}, encodeBytes, new byte[]{0x5D});
    }


    @Test
    public void sendMedicineList(){
        String[] array = new String[]{
                "0000000000010306060002190725190018B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190725134405B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190724190006B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190725081019B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190724134524B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190724075559B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190723190039B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190723130021B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190723075000B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190723075000B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190722190038B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F",
                "0000000000010306060002190722130010B8F1BBAAD6B9000000000000000000000000000000000000000000000000000000000000000000000000000F"
        };
        for(String body : array){
            SocketTool.request(content("040100220306003D",body));
        }
    }


    @Test
    public void sendReferList(){
        //5B0401001D03030013000000000001030606000219072612153800414D5D
        String[] array = new String[]{
                "00000000000103060600021907261215380041",
                "00000000000103060600021907250750570048",
                "00000000000103060600021907241220120039",
                "00000000000103060600021907231239290045",
                "0000000000010306060002190722121003003C"
        };
        for(String body : array){
            SocketTool.request(content("0401001D03030013",body));
        }
    }


    @Test
    public void sendFoodList(){
        //5B040100210304005200000000000103060600021907260720240A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C00007C5D
        String[] array = new String[]{
                "000000000001030606000219072212204314000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "000000000001030606000219072312414814000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907222100571E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907230730140A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907240720390A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "000000000001030606000219072322005500CEF7B9CF0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907232000231E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "000000000001030606000219072412205614000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907250755320A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907241930491E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "000000000001030606000219072512204214000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "000000000001030606000219072612202914000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907252000541E000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000",
                "00000000000103060600021907260720240A000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003C0000"

        };
        for(String body : array){
            SocketTool.request(content("0401002103040052",body));
        }
    }

}
