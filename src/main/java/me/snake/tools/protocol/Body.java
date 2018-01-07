package me.snake.tools.protocol;


import com.alibaba.fastjson.JSONObject;
import me.snake.tools.Constants;
import me.snake.tools.config.Command;
import me.snake.tools.config.Parameter;
import me.snake.tools.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Body {

    JSONObject json;
    String commandType;

    Attribute[] attributes;
    byte[] bytes;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public JSONObject getDefaultJson(){
        if (null != attributes && attributes.length > 0) {
            JSONObject json = new JSONObject();
            for (int i = 0; i < attributes.length; i++) {
                Attribute each = attributes[i];
                    json.put(each.getCode(),each.getValue());
            }
            return json;
        }
        return null;
    }

    public Body(Attribute[] attributes) {
        this(attributes, Command.command_type_byte);
    }

    public Body(Attribute[] attributes,String commandType) {
        this.attributes = attributes;
        this.commandType = commandType;
    }


    public Attribute[] getAttributes() {
        return attributes;
    }

    public byte[] encode() throws UnsupportedEncodingException {
        if (null != attributes && attributes.length > 0) {
            if(Command.command_type_json.equals(commandType)){
                bytes = Attribute.parseByteArray(getDefaultJson().toJSONString(), Parameter.byte_type_string,0);
            }else{
                bytes = new byte[0];
                for (int i = 0; i < attributes.length; i++) {
                    Attribute attribute = attributes[i];
                    if(null != json) {
                        attribute.setValue(json.get(attribute.getCode()));
                    }
                    bytes = ByteUtil.concat(bytes, attribute.encode());
                }

            }
            return bytes;
        }
        return null;
    }

    public boolean decode() throws UnsupportedEncodingException {
        if (null != bytes && bytes.length > 0 && null != attributes && attributes.length > 0) {
            if(Command.command_type_json.equals(commandType)){
                json = JSONObject.parseObject(ByteUtil.byte2string(bytes));
            }else {
                json = new JSONObject();
                int offset = 0;
                for (int i = 0; i < attributes.length; i++) {
                    Attribute attribute = attributes[i];
                    if (attribute.getByteLength() > 0) {
                        byte[] data = new byte[attribute.getByteLength()];
                        System.arraycopy(bytes, offset, data, 0, data.length);
                        attribute.setBytes(data);
                        offset += data.length;
                    } else {
                        attribute.setBytes(bytes);
                    }
                    attribute.decode();
                    json.put(attribute.getCode(), attribute.getValue());
                }
            }
        }
        return false;
    }

}
