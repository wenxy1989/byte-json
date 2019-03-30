package com.snake.data.config;

public class Parameter {

    private String name;
    private String code;
    private String type;

    private Type parameterType;
    private Parameter[] modelContent;
    private Object valueProvider;

    public boolean isModel(){
        return this.modelContent != null && this.modelContent.length > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Type getParameterType() {
        return parameterType;
    }

    public void setParameterType(Type parameterType) {
        this.parameterType = parameterType;
    }

    public Parameter[] getModelContent() {
        return modelContent;
    }

    public void setModelContent(Parameter[] modelContent) {
        this.modelContent = modelContent;
    }

    public Object getValueProvider() {
        return valueProvider;
    }

    public void setValueProvider(Object valueProvider) {
        this.valueProvider = valueProvider;
    }
}
