package com.snake.data.translate;

import com.snake.data.config.Parameter;
import com.snake.tools.utils.ByteUtil;

import java.util.List;

public class ParameterAnalysts {

    private Parameter parameter;
    private ByteProvider byteProvider;
    private TranslateManager manager;

    public ParameterAnalysts(Parameter parameter, ByteProvider byteProvider, TranslateManager manager) {
        this.parameter = parameter;
        this.byteProvider = byteProvider;
        this.manager = manager;
        this.byteProvider.setParameter(parameter);
    }

    public Object getModelValue(Config config, byte[] bytes) {
        for (int i = 0; i < this.parameter.getModelContent().length; i++) {
            Parameter parameter = this.parameter.getModelContent()[i];
            ParameterAnalysts parameterAnalysts = new ParameterAnalysts(parameter, byteProvider, this.manager);
            bytes = ByteUtil.concat(bytes, parameterAnalysts.getValue(config, bytes));
        }
        return null;
    }

    public List getArrayValue() {

        return null;
    }

    public TranslateChain getTranslateByteChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateByteChain());
    }

    public Object getValue(Config config, byte[] bytes) throws Exception {
        if (null != bytes) {
            Object result = null;
            if (this.parameter.isArray()) {
                if (this.parameter.isModel()) {
                    result = getArrayValue(config, bytes);
                } else {
                    throw new Exception("data error for : " + this.valueProvider.valueString());
                }
            } else {
                if (this.parameter.isModel()) {
                    result = getArrayValue(config, bytes);
                } else {
                    throw new Exception("data error for : " + this.valueProvider.valueString());
                }
            }
            TranslateChain chain = getTranslateByteChain(config);
            throw new Exception("no translate defined for " + this.parameter.getType() + " at " + this.parameter.getCode());
        } else {
            throw new Exception("null value for " + this.parameter.getType() + " at " + this.parameter.getCode());
        }
    }
}
