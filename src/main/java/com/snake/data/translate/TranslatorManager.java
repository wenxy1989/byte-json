package com.snake.data.translate;

import java.util.HashMap;
import java.util.Map;

public class TranslatorManager {

    private Map<String, Object> translatorMap;

    private TranslatorManager() {
        this.translatorMap = new HashMap<>();
    }

    public static TranslatorManager build() {
        return new TranslatorManager();
    }

    public void addTranslator(String... translators) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (int i = 0; i < translators.length; i++) {
            this.addTranslator(Class.forName(translators[i]));
        }
    }

    private String getKey(Class translatorClass) {
        return translatorClass.getName();
    }

    public boolean addTranslator(Class translatorClass) throws IllegalAccessException, InstantiationException {
        if (null == this.translatorMap.get(getKey(translatorClass))) {
            this.translatorMap.put(getKey(translatorClass), translatorClass.newInstance());
            return true;
        }
        return false;
    }

    public Object getTranslator(Class translatorClass) {
        return this.translatorMap.get(getKey(translatorClass));
    }

}
