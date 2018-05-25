package me.snake.tools.protocol;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.snake.tools.adapter.ByteTranslatorManager;
import me.snake.tools.config.Command;
import me.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Body {

    private Attribute[] attributes;
    private Command.Type commandType;

    private JSONArray jsonArray;

    public Body(Attribute[] attributes, Command.Type commandType) {
        this.attributes = attributes;
        this.commandType = commandType;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    private int getByteLength() {
        int length = 0;
        if (null != attributes && attributes.length > 0) {
            for (int i = 0; i < attributes.length; i++) {
                length += attributes[i].getLength();
            }
        }
        return length;
    }

    public byte[] encode(JSONArray data) {
        if (null != data && data.size() > 0) {
            byte[] bytes = new byte[0];
            for (int i = 0; i < data.size(); i++) {
                JSONObject json = data.getJSONObject(i);
                byte[] eachBytes = encode(json);
                bytes = ByteUtil.concat(bytes, eachBytes);
            }
            return bytes;
        }
        return null;
    }

    public byte[] encode(JSONObject json) {
        if (null != json) {
            return encode(attributes, commandType, json);
        }
        return null;
    }

    public static byte[] encode(Attribute[] attributes, Command.Type commandType, JSONObject json) {
        if (null != attributes && attributes.length > 0) {
            if (Command.Type.type_json.equals(commandType)) {
                return ByteTranslatorManager.encode(json.toJSONString(), ByteTranslatorManager.type_string, 0, 0);
            } else {
                byte[] bytes = new byte[0];
                for (int i = 0; i < attributes.length; i++) {
                    Attribute attribute = attributes[i];
                    attribute.setValue(json.get(attribute.getCode()));
                    bytes = ByteUtil.concat(bytes, attribute.encode());
                }
                return bytes;
            }
        }
        return null;
    }

    public boolean decode(byte[] bytes) throws UnsupportedEncodingException {
        return null != (jsonArray = decode(attributes, commandType, bytes));
    }

    public static JSONArray decode(Attribute[] attributes, Command.Type commandType, byte[] bytes) {
        if (null != bytes && bytes.length > 0) {
            if (Command.Type.type_json.equals(commandType)) {
                String jsonString = ByteUtil.byte2string(bytes);
                if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                    return JSONArray.parseArray(jsonString);
                }
                JSONObject json = JSONObject.parseObject(jsonString);
                JSONArray array = new JSONArray();
                array.add(json);
                return array;
            } else if(null != attributes && attributes.length > 0){
                int offset = 0;
                JSONArray array = new JSONArray();
                {
                    JSONObject json = new JSONObject();
                    for (int i = 0; i < attributes.length; i++) {
                        Attribute attribute = attributes[i];
                        if (attribute.getLength() > 0) {
                            byte[] data = new byte[attribute.getLength()];
                            System.arraycopy(bytes, offset, data, 0, data.length);
                            attribute.setBytes(data);
                            offset += data.length;
                        } else {
                            attribute.setBytes(bytes);
                            offset += bytes.length;
                        }
                        attribute.decode();
                        json.put(attribute.getCode(), attribute.getValue());
                    }
                    array.add(json);
                }
                while (offset < bytes.length) ;
                return array;
            }
        }
        return null;
    }

}
