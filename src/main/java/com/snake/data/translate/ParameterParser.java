package com.snake.data.translate;

import com.snake.data.config.Parameter;
import com.snake.data.json.ArrayProvider;
import com.snake.data.json.ObjectProvider;
import com.snake.tools.utils.ByteUtil;


public class ParameterParser {

    private Parameter parameter;
    private ObjectProvider objectProvider;
    private TranslateManager manager;

    public ParameterParser(Parameter parameter, ObjectProvider objectProvider, TranslateManager manager) {
        this.parameter = parameter;
        this.objectProvider = objectProvider;
        this.manager = manager;
    }

    public TranslateChain getTranslateChain(Config config) throws Exception {
        config.setParameterType(parameter.getParameterType());
        return this.manager.getTranslateChain(config, this.parameter.getParameterType().getTranslateToByteChain());
    }

    public byte[] getModelBytes(ObjectProvider objectProvider, Config config) throws Exception {
        byte[] bytes = new byte[0];
        for (int i = 0; i < this.parameter.getModelContent().length; i++) {
            Parameter parameter = this.parameter.getModelContent()[i];
            ParameterParser parameterParser = new ParameterParser(parameter, objectProvider, this.manager);
            bytes = ByteUtil.concat(bytes, parameterParser.getBytes(config));
        }
        return bytes;
    }

    public byte[] getArrayBytes(ArrayProvider arrayProvider, Config config) throws Exception {
        int length = arrayProvider.size();
        byte[] bytes = ByteUtil.int2byte(length);
        TranslateChain chain = getTranslateChain(config);
        for (int i = 0; i < length; i++) {
            Object value = arrayProvider.getValue(i);
            if (this.parameter.isModel()) {
                bytes = ByteUtil.concat(bytes, getModelBytes((ObjectProvider) value, config));
            } else {
                bytes = ByteUtil.concat(bytes, (byte[]) chain.translate(value));
            }
        }
        return bytes;
    }

    public byte[] getBytes(Config config) throws Exception {
        Object temp = null;
        if (this.parameter.isArray() != this.objectProvider.isArray(this.parameter)) {
            throw new Exception("is array defined and data difference :" + this.objectProvider.toString());
        } else if (this.parameter.isArray() && this.objectProvider.isArray(this.parameter)) {
            temp = getArrayBytes((ArrayProvider) this.objectProvider.getValue(this.parameter), config);
        } else if (this.parameter.isModel() && this.objectProvider.isObject(this.parameter)) {
            temp = getModelBytes((ObjectProvider) this.objectProvider.getValue(this.parameter), config);
        } else if (this.parameter.isModel() != this.objectProvider.isObject(this.parameter)) {
            throw new Exception("is model or object defined and data difference : " + this.objectProvider.toString());
        } else {
            temp = this.objectProvider.getValue(this.parameter);
        }
        TranslateChain chain = getTranslateChain(config);
        if (null != chain) {
            return (byte[]) chain.translate(temp);
        } else if (temp instanceof byte[]) {
            return (byte[]) temp;
        } else {
            throw new Exception("no translate defined for " + this.parameter.getType() + " at " + this.parameter.getCode());
        }
    }

}
