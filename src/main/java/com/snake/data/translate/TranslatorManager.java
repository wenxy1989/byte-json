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
        this.translatorMap.put(translator.getCode(),translator);
        return true;//todo
    }

    public Translator getTranslator(String code){
        return this.translatorMap.get(code);
    }

    public TranslateChain getTranslateChain(Type type,boolean debug) throws Exception {
        TranslateChain chain = new TranslateChain(type,debug);
        Translator[] translators = new Translator[type.getTranslatorChain().length];
        for(int i=0;i<type.getTranslatorChain().length;i++){
            translators[i] = this.getTranslator(type.getTranslatorChain()[i]);
            if(translators[i] != null) {
                translators[i].setConfig(type);//todo
                translators[i].debug(debug);
            }else{
                throw new Exception("not found translate for code : "+ type.getTranslatorChain()[i]); //todo
            }
        }
        chain.setTranslators(translators);
        return chain;
    }

    public TranslateChain getTranslateChain(Type type) throws Exception {
        return this.getTranslateChain(type,false);
    }
}
