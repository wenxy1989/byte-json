package com.snake.data.translate;

import com.snake.data.config.Type;

import java.util.HashMap;
import java.util.Map;

public class TranslatorManager {

    private Map<String,Translator> translatorMap;

    public TranslatorManager(){
        this.translatorMap = new HashMap<>();
    }

    public boolean addTranslator(Translator translator){
        if(null == this.translatorMap.get(translator.getCode())) {
            this.translatorMap.put(translator.getCode(), translator);
            return true;
        }
        return false;
    }

    public Translator getTranslator(String code){
        return this.translatorMap.get(code);
    }

    public TranslateChain getTranslateChain(Config config,String... translateChain) throws Exception {
        if(null == config){
            throw new Exception("config can not been null");
        }
        TranslateChain chain = new TranslateChain(config,translateChain);
        Translator[] translators = new Translator[translateChain.length];
        for(int i=0;i<translateChain.length;i++){
            translators[i] = this.getTranslator(translateChain[i]);
            if(translators[i] != null) {
                translators[i].setConfig(config);
            }else{
                throw new Exception("not found translate for code : "+ translateChain[i]);
            }
        }
        chain.setTranslators(translators);
        return chain;
    }

}
