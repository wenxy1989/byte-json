package com.snake.data.config;

public class Type {
    private String code;//写作方式
    private String[] translatorChain;//处理链
    private String jsonType;//json格式类型
    private String javaType;//java格式类型
    private int byteLength;//占用字节数
    private int decimal;//当类型为浮点数时的小数点后精度
    private String optionalValue;//当取值范围固定时，可取值

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getTranslatorChain() {
        return translatorChain;
    }

    public void setTranslatorChain(String... translatorChain) {
        this.translatorChain = translatorChain;
    }

    public String getJsonType() {
        return jsonType;
    }

    public void setJsonType(String jsonType) {
        this.jsonType = jsonType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public int getByteLength() {
        return byteLength;
    }

    public void setByteLength(int byteLength) {
        this.byteLength = byteLength;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getOptionalValue() {
        return optionalValue;
    }

    public void setOptionalValue(String optionalValue) {
        this.optionalValue = optionalValue;
    }
}
