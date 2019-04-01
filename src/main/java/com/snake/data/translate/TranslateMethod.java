package com.snake.data.translate;

import java.lang.reflect.Method;

public class TranslateMethod {

    private Class translatorClass;
    private Method translateMethod;

    public TranslateMethod(){}

    public TranslateMethod(Class translatorClass,Method translateMethod){
        this.translatorClass = translatorClass;
        this.translateMethod = translateMethod;
    }

    public Class getTranslatorClass() {
        return translatorClass;
    }

    public void setTranslatorClass(Class translatorClass) {
        this.translatorClass = translatorClass;
    }

    public Method getTranslateMethod() {
        return translateMethod;
    }

    public void setTranslateMethod(Method translateMethod) {
        this.translateMethod = translateMethod;
    }
}
