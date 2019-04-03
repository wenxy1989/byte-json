package com.sanmeditech.translator;

import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;

@Translator
public class ByteTranslator {

    @Translate("long-datetime-string")
    public String long2datetime(long value){
        return String.valueOf(value);
    }

}
