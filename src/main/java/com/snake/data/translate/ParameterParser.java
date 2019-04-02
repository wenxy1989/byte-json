package com.snake.data.translate;

import com.snake.data.config.Parameter;
import com.snake.data.json.ValueProvider;
import com.snake.tools.utils.ByteUtil;

import java.util.List;


public class ParameterParser {

    private Parameter parameter;
    private ValueProvider valueProvider;
    private TranslateManager manager;

    public ParameterParser(Parameter parameter, ValueProvider valueProvider, TranslateManager manager) {
        this.parameter = parameter;
        this.valueProvider = valueProvider;
        this.manager = manager;
        this.valueProvider.setParameter(this.parameter);
    }

    public boolean isObject() {
        return this.valueProvider.isObject();
    }

    public boolean isArray() {
        return this.valueProvider.isArray();
    }

    public TranslateChain getTranslateChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateToByteChain());
    }

    public byte[] getModelBytes(ValueProvider valueProvider, Config config) throws Exception {
        byte[] bytes = new byte[0];
        for (int i = 0; i < this.parameter.getModelContent().length; i++) {
            Parameter parameter = this.parameter.getModelContent()[i];
            ParameterParser parameterParser = new ParameterParser(parameter, valueProvider, this.manager);
            bytes = ByteUtil.concat(bytes, parameterParser.getBytes(config));
        }
        return bytes;
    }

    public byte[] getArrayBytes(Config config) throws Exception {
        int length = this.valueProvider.size();
        byte[] bytes = ByteUtil.int2byte(length);
        TranslateChain chain = getTranslateChain(config);
        for (int i = 0; i < length; i++) {
            Object value = this.valueProvider.getValue(i);
            if (this.parameter.isModel()) {
                bytes = ByteUtil.concat(bytes, getModelBytes((ValueProvider) value, config));
            } else {
                bytes = ByteUtil.concat(bytes, (byte[]) chain.translate(value));
            }
        }
        return bytes;
    }

    public byte[] getBytes(Config config) throws Exception {
        Object value = this.valueProvider.getValue();
        if (null != value) {
            Object temp = null;
            if (this.parameter.isArray() != this.valueProvider.isArray()) {
                throw new Exception("is array defined and data difference :" + this.valueProvider.valueString());
            } else if (this.parameter.isArray() && this.valueProvider.isArray()) {
                temp = getArrayBytes(config);
            } else if (this.parameter.isModel() && this.valueProvider.isObject()) {
                temp = getModelBytes((ValueProvider) value, config);
            } else if (this.parameter.isModel() != this.valueProvider.isObject()) {
                throw new Exception("is model or object defined and data difference : " + this.valueProvider.valueString());
            } else {
                temp = value;
            }
            TranslateChain chain = getTranslateChain(config);
            if (null != chain) {
                return (byte[]) chain.translate(temp);
            } else if (temp instanceof byte[]) {
                return (byte[]) temp;
            } else {
                throw new Exception("no translate defined for " + this.parameter.getType() + " at " + this.parameter.getCode());
            }
        } else {
            throw new Exception("null value for " + this.parameter.getType() + " at " + this.parameter.getCode());
        }
    }

}
