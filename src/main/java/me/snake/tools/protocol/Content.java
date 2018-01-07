package me.snake.tools.protocol;


import com.alibaba.fastjson.JSONObject;
import me.snake.tools.config.Command;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.utils.ByteUtil;
import me.snake.tools.utils.CodeParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Content {

    JSONObject json;

    Head head;
    Body body;

    byte[] bytes;
    byte[] encodeBytes;
    byte[] decodeBytes;
    byte[] infoBytes;
    byte[] headBytes;
    byte[] bodyBytes;
    byte flag;

    public Content(byte[] bytes) {
        this.bytes = bytes;
    }

    public Content(Head head, Body body) {
        this.head = head;
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public static byte start() {
        return 0x5b;
    }

    public static byte end() {
        return 0x5d;
    }

    public byte check(byte[] info) {
        return flag = CodeParser.check(info);
    }

    public byte[] encode() throws UnsupportedEncodingException {
        return encode(null);
    }
    public byte[] encode(JSONObject json) throws UnsupportedEncodingException {
        if (null != head && null != body) {
            body.setJson(json);
            bodyBytes = body.encode();
            head.setLength(bodyBytes.length);
            headBytes = head.encode();
            infoBytes = ByteUtil.concat(headBytes,bodyBytes);
            decodeBytes = ByteUtil.concat(infoBytes, check(infoBytes));
            encodeBytes = CodeParser.encode(decodeBytes);
            return bytes = ByteUtil.concat(new byte[]{start()}, encodeBytes, new byte[]{end()});
        } else {
            return null;
        }
    }

    public boolean decode() throws IOException {
        if (null != bytes && bytes[0] == start() && bytes[bytes.length - 1] == end()) {
            encodeBytes = new byte[bytes.length - 2];
            System.arraycopy(bytes, 1, encodeBytes, 0, encodeBytes.length);
            decodeBytes = CodeParser.decode(encodeBytes);
            flag = decodeBytes[decodeBytes.length - 1];
            infoBytes = new byte[decodeBytes.length - 1];
            System.arraycopy(decodeBytes, 0, infoBytes, 0, decodeBytes.length - 1);
            if (check(infoBytes) == flag) {
                System.arraycopy(infoBytes, 0, headBytes, 0, headBytes.length);
                head = new Head(headBytes);
                head.decode();
                body = ConfigTools.buildBody(head.getCommand());
                body.setBytes(bodyBytes);
                body.decode();
                return true;
            }
        }
        return false;
    }

}
