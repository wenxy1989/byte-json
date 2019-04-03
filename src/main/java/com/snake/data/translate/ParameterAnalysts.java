package com.snake.data.translate;

import com.snake.data.config.Parameter;
import com.snake.tools.utils.ByteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public TranslateChain getTranslateChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateByteChain());
    }

    public Map<String, Object> getModelValue(Config config) throws Exception {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < this.parameter.getModelContent().length; i++) {
            Parameter child = this.parameter.getModelContent()[i];
            ParameterAnalysts parameterAnalysts = new ParameterAnalysts(child, byteProvider, this.manager);
            Object value = parameterAnalysts.getValue(config);
            map.put(child.getCode(), value);
        }
        return map;
    }

    public List getArrayValue(Config config) throws Exception {
        int length = this.byteProvider.getByteLength();
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (this.parameter.isModel()) {
                list.add(getModelValue(config));
            } else {
                ParameterAnalysts parameterAnalysts = new ParameterAnalysts(this.parameter, byteProvider, this.manager);
                list.add(parameterAnalysts.getValue(config));
            }
        }
        return list;
    }

    public Object getValue(Config config) throws Exception {
        if (null != this.byteProvider) {
            Object result = null;
            if (this.parameter.isArray()) {
                return getArrayValue(config);
            } else {
                if (this.parameter.isModel()) {
                    return getModelValue(config);
                } else {
                    TranslateChain chain = getTranslateChain(config);
                    if (null != chain) {
                        return chain.translate(this.byteProvider.getBytes());
                    } else {
                        throw new Exception("no translate defined for " + this.parameter.getType() + " at " + this.parameter.getCode());
                    }
                }
            }
        } else {
            throw new Exception("null value for " + this.parameter.getType() + " at " + this.parameter.getCode());
        }
    }
}
