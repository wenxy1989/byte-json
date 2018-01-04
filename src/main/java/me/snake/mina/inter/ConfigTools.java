package me.snake.mina.inter;

import me.snake.mina.config.Command;
import me.snake.mina.config.Parameter;
import me.snake.mina.config.Server;
import me.snake.mina.protocol.Attribute;
import me.snake.mina.protocol.Body;
import me.snake.mina.protocol.Content;
import me.snake.mina.protocol.Head;
import me.snake.mina.utils.BCDByteUtil;
import me.snake.mina.utils.ByteUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenxy on 2018/1/1.
 */
public class ConfigTools {


    public static String int2Code(int command) {
        byte[] bytes = ByteUtil.int2byte(command);
        return BCDByteUtil.hexString(bytes);
    }

    public static int code2Int(String code) {
        short command = Short.parseShort(code);
        return ByteUtil.byte2int(BCDByteUtil.short2bcd(command));
    }

    public static void setConfigFileName(String fileName) {
        Server.setConfigFileName(fileName);
    }

    public static Command getCommand(String code) throws IOException {
        return Server.getInstance().getCommandMap().get(code);
    }

    public static Command getCommand(int code) throws IOException {
        return Server.getInstance().getCommandMap().get(int2Code(code));
    }

    public static Attribute[] buildAttributes(List<Parameter> parameters) {
        Attribute[] attributes = new Attribute[parameters.size()];
        for (int i = 0; i < attributes.length; i++) {
            Parameter parameter = parameters.get(i);
            attributes[i] = new Attribute(parameter.getCode(), parameter.getJavaType(), parameter.getByteType(), parameter.getByteLength(), parameter.getDefaultValue());
        }
        return attributes;
    }

    public static Attribute[] buildAttributes(String code) throws IOException {
        Command command = getCommand(code);
        if (null != command) {
            return buildAttributes(command.getParameters());
        }
        return null;
    }

    public static Attribute[] buildAttributes(int com) throws IOException {
        return buildAttributes(int2Code(com));
    }

    public static Content buildContent(Command command) {
        if (null != command) {
            Body body = new Body(buildAttributes(command.getParameters()));
            int code = code2Int(command.getCode());
            Head head = new Head(0x0400, 0, code);
            return new Content(head, body);
        }
        return null;
    }

}
