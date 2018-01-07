package me.snake.tools.inter;

import com.alibaba.fastjson.JSONObject;
import me.snake.tools.config.Command;
import me.snake.tools.protocol.Attribute;
import me.snake.tools.protocol.Content;

import java.io.IOException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class JsonByte {

    public byte[] json2Byte(JSONObject json) {
        if (null != json) {
            String code = json.getString("code");
            try {
                Command command = ConfigTools.getCommand(code);
                Content content = ConfigTools.buildContent(command);
                if (null != content) {
                    return content.encode(json);
                } else {
                    System.out.println(" build content from command error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public JSONObject byte2Json(byte[] bytes) {
        if (null != bytes && bytes.length > 0) {
            try {
                Content content = new Content(bytes);
                if (content.decode()) {
                    JSONObject json = new JSONObject();
                    for (int i = 0; i < content.getBody().getAttributes().length; i++) {
                        Attribute attribute = content.getBody().getAttributes()[i];
                        json.put(attribute.getCode(), attribute.getValue());
                    }
                    return json;
                } else {
                    System.out.println("decode byte[] head error ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("no command  config found ");
        }
        return null;
    }

}
