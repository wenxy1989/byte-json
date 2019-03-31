package com.snake.data.translate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.config.Type;
import com.snake.tools.utils.FileStringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TranslatorManager {

    private Map<String,Translator> translatorMap;

    public TranslatorManager(){
        this.translatorMap = new HashMap<>();
    }

    public static String[] getTranslatorClass(String base,JSONArray array){
        String[] translators = new String[array.size()];
        for(int i=0;i<array.size();i++){
            translators[i] = base + "." + array.getString(i);
        }
        return translators;
    }

    public static TranslatorManager build(String jsonFile) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        JSONObject config = FileStringUtils.readClassPathJSONObject(jsonFile);
        JSONArray array = config.getJSONArray("translator");
        TranslatorManager manager = new TranslatorManager();
        for(int i=0;i<array.size();i++){
            JSONObject json = array.getJSONObject(i);
            String base = json.getString("base");
            JSONArray translators = json.getJSONArray("translators");
            manager.buildTranslator(getTranslatorClass(base,translators));
        }
        return manager;
    }

    public void buildTranslator(String... tranlators) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for(int i=0;i<tranlators.length;i++) {
            Class<Translator> translatorClass = (Class<Translator>) Class.forName(tranlators[i]);
            Translator translator = translatorClass.newInstance();
            this.addTranslator(translator);
        }
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
