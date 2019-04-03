package com.snake.data.translate;


import com.snake.data.config.Parameter;
import com.snake.tools.Constants;
import com.snake.tools.utils.ByteUtil;


public class ByteProvider {

    private int position = 0;
    private byte[] bytes;
    private Parameter parameter;

    public ByteProvider(byte[] bytes) {
        this.bytes = bytes;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public boolean validate() {
        return null != this.parameter && null != this.bytes && this.position < this.bytes.length;
    }

    public static byte[] getBytes(byte[] bytes, int offset, int length) {
        assert null != bytes && bytes.length >= length + offset;
        byte[] result = new byte[length];
        System.arraycopy(bytes, offset, result, 0, length);
        return result;
    }

    public byte[] getBytes(int length) {
        byte[] result = getBytes(this.bytes, this.position, length);
        this.position += length;
        return result;
    }

    public int getByteLength() {
        return ByteUtil.byte2int(getBytes(Constants.LENGTH_BYTE_LENGTH));
    }

    public int getLength() throws Exception {
        int length = 0;
        if (this.validate()) {
            if (this.parameter.getParameterType().isStaticLength()) {
                length = this.parameter.getParameterType().getByteLength();
            } else {
                length = getByteLength();
            }
        }else {
            throw new Exception("provider is illegal");
        }
        return length;
    }

    public byte[] getBytes() throws Exception {
        return getBytes(getLength());
    }

}
