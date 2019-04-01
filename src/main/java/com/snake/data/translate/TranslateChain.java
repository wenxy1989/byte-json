package com.snake.data.translate;

import com.snake.data.config.ParameterType;

import java.lang.reflect.Method;

public class TranslateChain {

    private Config config;
    private TranslatorManager translatorManager;
    private TranslateMethod[] translateChain;

    public TranslateChain(TranslateMethod[] translateChain, TranslatorManager translatorManager) {
        this.translateChain = translateChain;
        this.translatorManager = translatorManager;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }

    public void setTranslatorManager(TranslatorManager translatorManager) {
        this.translatorManager = translatorManager;
    }

    public TranslateMethod[] getTranslateChain() {
        return translateChain;
    }

    public void setTranslateChain(TranslateMethod[] translateChain) {
        this.translateChain = translateChain;
    }

    private boolean needType(Method method) {
        Class[] classes = method.getParameterTypes();
        for (int i = 0; i < classes.length; i++) {
            if (classes[i].equals(ParameterType.class)) {
                return true;
            }
        }
        return false;
    }

    public Object translate(final Object input) throws Exception {
        Object temp = input;
        for (TranslateMethod translateMethod : this.translateChain) {
            Object translator = this.translatorManager.getTranslator(translateMethod.getTranslatorClass());
            Object[] parameters = null;
            if (needType(translateMethod.getTranslateMethod())) {
                parameters = new Object[]{temp, config.getParameterType()};
            } else {
                parameters = new Object[]{temp};
            }
            try {
                temp = translateMethod.getTranslateMethod().invoke(translator, parameters);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return temp;
    }

}
