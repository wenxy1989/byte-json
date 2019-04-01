package com.snake.data.translate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.data.translate.annocation.Translate;
import com.snake.data.translate.annocation.Translator;
import com.snake.tools.utils.FileStringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TranslateManager {

    private TranslatorManager translatorManager;
    private Map<String, TranslateMethod> translateMethodMap;
    private Map<String, TranslateChain> translateChainMap;

    private TranslateManager() {
        this.translateMethodMap = new HashMap<>();
        this.translateChainMap = new HashMap<>();
    }

    public static TranslateManager build(String jsonFile) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        TranslateManager manager = new TranslateManager();
        manager.translatorManager = TranslatorManager.build();

        JSONObject config = FileStringUtils.readClassPathJSONObject(jsonFile);
        JSONArray array = config.getJSONArray("translator");
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            String base = json.getString("base");
            JSONArray translators = json.getJSONArray("translators");
            manager.buildTranslator(getTranslatorClass(base, translators));
        }
        return manager;
    }

    public static String[] getTranslatorClass(String base, JSONArray array) {
        String[] translators = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            translators[i] = base + "." + array.getString(i);
        }
        return translators;
    }

    public void buildTranslator(String... translators) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.translatorManager.addTranslator(translators);
        for (int i = 0; i < translators.length; i++) {
            Class clazz = Class.forName(translators[i]);
            this.addTranslator(clazz);
        }
    }

    public boolean addTranslator(Class clazz) throws InstantiationException, IllegalAccessException {
        Annotation translator = clazz.getAnnotation(Translator.class);
        if (null != translator) {
            String head = ((Translator) translator).value();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Annotation translate = method.getAnnotation(Translate.class);
                if (null != translate) {
                    String key = head + ((Translate) translate).value();
                    if (this.translateChainMap.containsValue(key)) {
                        return false;
                    } else {
                        this.translateMethodMap.put(key, new TranslateMethod(clazz, method));
                    }
                }
            }
            return true;
        }
        return false;
    }

    public TranslateChain buildTranslateChain(String... translate) throws Exception {
        TranslateMethod[] translateMethods = new TranslateMethod[translate.length];
        for (int i = 0; i < translate.length; i++) {
            translateMethods[i] = this.translateMethodMap.get(translate[i]);
            if(translateMethods[i] == null){
                throw new Exception(translate[i]+" have no mapped method");
            }

        }
        return new TranslateChain(translateMethods, this.translatorManager);
    }

    public TranslateChain getTranslateChain(Config config, String... translate) throws Exception {
        String key = Arrays.toString(translate);//todo
        if (null == this.translateChainMap.get(key)) {
            this.translateChainMap.put(key, this.buildTranslateChain(translate));
        }
        TranslateChain translateChain = this.translateChainMap.get(key);
        translateChain.setConfig(config);
        return translateChain;
    }

}
