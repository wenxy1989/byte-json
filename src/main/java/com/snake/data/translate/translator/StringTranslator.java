package com.snake.data.translate.translator;

import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
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

}
