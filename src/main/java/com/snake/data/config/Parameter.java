package com.snake.data.config;

public class Parameter {

    private String name;
    private String code;
    private String type;
    private boolean array = false;

    private ParameterType parameterType;
    private Parameter[] modelContent;
    private Object valueProvider;

    public boolean isModel(){
        return "model".equals(this.type) && this.modelContent != null && this.modelContent.length > 0;
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

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
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

    public boolean isArray() {
        return this.array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public boolean validate() {
        return this.code != null && this.type != null;
    }
}
