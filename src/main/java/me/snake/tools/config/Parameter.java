package me.snake.tools.config;

/**
 * Created by HP on 2017/12/27.
 */
public class Parameter {

    private static final String java_type_long = "long";
    private static final String java_type_integer = "int";
    private static final String java_type_short = "short";
    private static final String java_type_char = "char";
    private static final String java_type_byte = "byte";
    private static final String java_type_double = "double";
    private static final String java_type_float = "float";
    private static final String java_type_boolean = "boolean";
    private static final String java_type_string = "string";
    private static final String java_type_date = "date";
    private static final String java_type_datetime = "datetime";

    public enum JavaType {
        type_date, type_datetime,
        type_string, type_double, type_float, type_long, type_integer, type_short, type_char, type_byte, type_boolean;

        public static JavaType getType(String type) {
            if (null == type) {
                return type_string;
            } else if (java_type_string.equals(type.toLowerCase())) {
                return type_string;
            } else if (java_type_double.equals(type.toLowerCase())) {
                return type_double;
            } else if (java_type_float.equals(type.toLowerCase())) {
                return type_float;
            } else if (java_type_long.equals(type.toLowerCase())) {
                return type_long;
            } else if (java_type_integer.equals(type.toLowerCase())) {
                return type_integer;
            } else if (java_type_short.equals(type.toLowerCase())) {
                return type_short;
            } else if (java_type_char.equals(type.toLowerCase())) {
                return type_char;
            } else if (java_type_byte.equals(type.toLowerCase())) {
                return type_byte;
            } else if (java_type_boolean.equals(type.toLowerCase())) {
                return type_boolean;
            }
            return type_string;
        }
    }

    public static final String byte_type_bcd = "bcd";
    public static final String byte_type_md5 = "md5";
    public static final String byte_type_byte = "string_bcd";
    public static final String byte_type_string = "string";

    public enum ByteType {
        type_default, type_bcd, type_byte, type_string,type_md5;

        public static ByteType getType(String byteType) {
            if (null != byteType) {
                return type_default;
            } else if (byte_type_bcd.equals(byteType)) {
                return type_bcd;
            } else if (byte_type_md5.equals(byteType)) {
                return type_md5;
            } else if (byte_type_string.equals(byteType)) {
                return type_string;
            }
            return type_default;
        }
    }

    private String name;
    private String code;
    private JavaType javaType;
    private ByteType byteType;
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
            setByteType(ByteType.getType(byteType));
        }
    }

    public void setByteType(ByteType byteType) {
        this.byteType = byteType;
    }

    private void setByteLengthByJavaType() {
        if (java_type_boolean.equals(javaType)) {
            setByteLength(1);
        } else if (java_type_byte.equals(javaType)) {
            setByteLength(1);
        } else if (java_type_char.equals(javaType)) {
            setByteLength(2);
        } else if (java_type_short.equals(javaType)) {
            setByteLength(2);
        } else if (java_type_integer.equals(javaType)) {
            setByteLength(4);
        } else if (java_type_long.equals(javaType)) {
            setByteLength(8);
        } else if (java_type_float.equals(javaType)) {
            setByteLength(4);
        } else if (java_type_double.equals(javaType)) {
            setByteLength(8);
        } else if (java_type_string.equals(javaType)) {
            setByteLength(0);
        } else if (java_type_date.equals(javaType)) {
            setByteLength(4);
        } else if (java_type_datetime.equals(javaType)) {
            setByteLength(8);
        }
    }

    public void setJavaType(String javaType) {
        setJavaType(JavaType.getType(javaType));
    }

    public void setJavaType(JavaType javaType) {
        this.javaType = javaType;
        setByteLengthByJavaType();
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

    public ByteType getByteType() {
        return byteType;
    }

    public JavaType getJavaType() {
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
