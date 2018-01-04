package me.snake.mina.config;

/**
 * Created by HP on 2017/12/27.
 */
public class Parameter {

    public static final String java_type_date = "date";
    public static final String java_type_datetime = "datetime";

    public static final String java_type_long = "long";
    public static final String java_type_integer = "integer";
    public static final String java_type_short = "short";
    public static final String java_type_char = "char";
    public static final String java_type_byte = "byte";

    public static final String java_type_string = "string";
    public static final String java_type_double = "double";
    public static final String java_type_float = "float";
    public static final String java_type_boolean = "boolean";

    public static final String byte_type_number = "number";
    public static final String byte_type_bcd = "bcd";
    public static final String byte_type_byte = "byte";
    public static final String byte_type_string = "string";

    private String name;
    private String code;
    private String javaType;
    private String byteType;
    private Integer byteLength;
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

    public void setByteLength(Integer byteLength) {
        if (null != byteLength && byteLength >= 0) {
            this.byteLength = byteLength;
        }
    }

    public void setByteType(String byteType) {
        if (null != byteType && byteType.trim().length() > 0) {
            this.byteType = byteType;
        }
    }

    public void setJavaType(String javaType) {
        if (java_type_boolean.equals(javaType)) {
            setByteLength(1);
        } else if (java_type_long.equals(javaType)) {
            setByteLength(4);
        } else if (java_type_integer.equals(javaType)) {
            setByteLength(2);
        } else if (java_type_short.equals(javaType)) {
            setByteLength(1);
//        }else if(java_type_double.equals(javaType)){
//            setByteLength(2);
        }
        if (null != javaType && javaType.trim().length() > 0) {
            this.javaType = javaType;
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

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Integer getByteLength() {
        return byteLength;
    }

    public String getByteType() {
        return byteType;
    }

    public String getJavaType() {
        return javaType;
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
        obj.setJavaType(javaType);
        obj.setByteType(byteType);
        obj.setByteLength(byteLength);
        obj.setDefaultValue(defaultValue);
        return obj;
    }
}
