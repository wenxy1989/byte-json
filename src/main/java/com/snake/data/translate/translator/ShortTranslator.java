package com.snake.data.translate.translator;

import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;

@Translator("short-")
public class ShortTranslator {

    @Translate("4-string")
    public String length4String(short value){
        return String.format("%04d",value);
    }

}
