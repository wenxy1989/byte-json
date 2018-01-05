package me.snake.tools.protocol;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Body {

    Attribute[] attributes;
    byte[] bytes;

    public Body(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public Body(Attribute[] attributes, byte[] bytes) {
        this.attributes = attributes;
        this.bytes = bytes;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    public byte[] encode() throws UnsupportedEncodingException {
        if (null != attributes && attributes.length > 0) {
            bytes = new byte[0];
            for (int i = 0; i < attributes.length; i++) {
                Attribute attribute = attributes[i];
                bytes = ByteUtil.concat(bytes, attribute.encode());
            }
            return bytes;
        }
        return null;
    }

    public byte[] encode(JSONObject json) throws UnsupportedEncodingException {
        if (null != attributes && attributes.length > 0) {
            bytes = new byte[0];
            for (int i = 0; i < attributes.length; i++) {
                Attribute attribute = attributes[i];
                attribute.setValue(json.get(attribute.getCode()));
                bytes = ByteUtil.concat(bytes, attribute.encode());
            }
            return bytes;
        }
        return null;
    }

    public boolean decode() throws UnsupportedEncodingException {
        if (null != bytes && bytes.length > 0 && null != attributes && attributes.length > 0) {
            int offset = 0;
            for (int i = 0; i < attributes.length; i++) {
                Attribute attribute = attributes[i];
                if (attribute.getByteLength() > 0) {
                    byte[] data = new byte[attribute.getByteLength()];
                    System.arraycopy(bytes, offset, data, 0, data.length);
                    attribute.setBytes(data);
                } else {
                    attribute.setBytes(bytes);
                }
                attribute.decode();
            }
        }
        return false;
    }

}
