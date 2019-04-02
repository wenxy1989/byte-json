package com.snake.data.translate.translator;

import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
import com.snake.tools.utils.BCDByteUtil;
import com.snake.tools.utils.HexByteUtil;

@Translator("string-")
public class StringTranslator {

    @Translate("trim")
    public String trim(String value) {
        return value.trim();
    }

    @Translate("hex-byte")
    public byte[] hexByte(String value) {
        return HexByteUtil.hexStringToByte(value);
    }

    @Translate("short")
    public short parseShort(String value) {
        return Short.parseShort(value);
    }

    @Translate("datetime-number")
    public long datetime2number(String datetime){
        return Long.parseLong(datetime.replaceAll(" ","").replaceAll("-","").replaceAll(":",""));
    }

}
