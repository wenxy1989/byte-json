package me.snake.tools.protocol;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.utils.ByteUtil;
import me.snake.tools.utils.CodeParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Content {

    private Head head;
    private Body body;

    public Content(Head head, Body body) {
        this.head = head;
        this.body = body;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public byte[] encode() throws UnsupportedEncodingException {
        return encode(null);
    }

    public byte[] encode(JSONObject json) throws UnsupportedEncodingException {
        return encode(head, body, json);
    }

    public byte[] encodeArray(JSONArray array) throws UnsupportedEncodingException {
        return encode(head, body, array);
    }

    public static byte start() {
        return 0x5b;
    }

    public static byte end() {
        return 0x5d;
    }

    public static byte check(byte[] info) {
        return CodeParser.check(info);
    }

    public static byte[] encode(Head head, Body body, JSONObject json) throws UnsupportedEncodingException {
        if (null != head && null != body) {
            body.setJson(json);
            byte[] bodyBytes = body.encode();
            head.setLength(bodyBytes.length);
            byte[] headBytes = head.encode();
            byte[] infoBytes = ByteUtil.concat(headBytes, bodyBytes);
            byte flag = check(infoBytes);
            byte[] decodeBytes = ByteUtil.concat(infoBytes, flag);
            byte[] encodeBytes = CodeParser.encode(decodeBytes);
            return ByteUtil.concat(new byte[]{start()}, encodeBytes, new byte[]{end()});
        } else {
            return null;
        }
    }

    public static byte[] encode(Head head, Body body, JSONArray array) throws UnsupportedEncodingException {
        if (null != head && null != body) {
            body.setJsonArray(array);
            byte[] bodyBytes = body.encode();
            head.setLength(bodyBytes.length);
            byte[] headBytes = head.encode();
            byte[] infoBytes = ByteUtil.concat(headBytes, bodyBytes);
            byte flag = check(infoBytes);
            byte[] decodeBytes = ByteUtil.concat(infoBytes, flag);
            byte[] encodeBytes = CodeParser.encode(decodeBytes);
            return ByteUtil.concat(new byte[]{start()}, encodeBytes, new byte[]{end()});
        } else {
            return null;
        }
    }

    public static Content decode(byte[] bytes) throws IOException {
        if (null != bytes && bytes[0] == start() && bytes[bytes.length - 1] == end()) {
            byte[] encodeBytes = new byte[bytes.length - 2];
            System.arraycopy(bytes, 1, encodeBytes, 0, encodeBytes.length);
            byte[] decodeBytes = CodeParser.decode(encodeBytes);
            byte flag = decodeBytes[decodeBytes.length - 1];
            byte[] infoBytes = new byte[decodeBytes.length - 1];
            System.arraycopy(decodeBytes, 0, infoBytes, 0, decodeBytes.length - 1);
            if (check(infoBytes) == flag) {
                byte[] headBytes = new byte[Head.byte_bit_length];
                System.arraycopy(infoBytes, 0, headBytes, 0, headBytes.length);

                Head head = new Head(headBytes);
                head.decode();
                byte[] bodyBytes = new byte[head.getLength()];
                System.arraycopy(infoBytes, headBytes.length, bodyBytes, 0, head.getLength());
                Body body = ConfigTools.buildBody(head.getCommand());
                if (body != null) {
                    body.decode(bodyBytes);
                    return new Content(head, body);
                }
            }
        }
        return null;
    }

}
