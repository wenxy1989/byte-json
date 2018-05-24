package me.snake.tools.config;

/**
 * Created by HP on 2017/12/27.
 */
public class Parameter {

    private String name;
    private String code;
    private String type;
    private int length;
    private int decimal;
    private Object defaultValue;

    public void setName(String name) {
        if (null != name && name.trim().length() > 0) {
            this.name = name;
        }
    }

    public void setCode(String code) {
        if (null != code && code.trim().length() > 0) {
            this.code = code;
        }
    }

    public void setLength(Integer length) {
        if (null != length && length >= 0) {
            this.length = length;
        }
    }

    public void setDecimal(Integer decimal) {
        if (null != decimal && decimal >= 0) {
            this.decimal = decimal;
        }
    }

    public void setDefaultValue(Object defaultValue) {
        if (null != defaultValue) {
            if (defaultValue instanceof String) {
                this.defaultValue = ((String) defaultValue).trim().length() > 0 ? defaultValue : this.defaultValue;
            } else {
                this.defaultValue = defaultValue;
            }
        }
    }

    public void setType(String type) {
        if (null != type) {
            this.type = type;
        }
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Parameter copy() {
        Parameter obj = new Parameter();
        obj.setName(name);
        obj.setCode(code);
        obj.setType(type);
        obj.setLength(length);
        obj.setDecimal(decimal);
        obj.setDefaultValue(defaultValue);
        return obj;
    }
}
