package com.snake.data.config;

public class Parameter {

    private String name;
    private String code;
    private String type;
    private String[] children;

    private Type parameterType;

    private Parameter[] parameterChildren;

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

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }

    public Type getParameterType() {
        return parameterType;
    }

    public void setParameterType(Type parameterType) {
        this.parameterType = parameterType;
    }

    public Parameter[] getParameterChildren() {
        return parameterChildren;
    }

    public void setParameterChildren(Parameter[] parameterChildren) {
        this.parameterChildren = parameterChildren;
    }
}
