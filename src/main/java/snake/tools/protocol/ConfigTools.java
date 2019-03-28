package snake.tools.protocol;

import snake.tools.config.Command;
import snake.tools.config.Parameter;
import snake.tools.config.Server;
import snake.tools.protocol.Attribute;
import snake.tools.protocol.Body;
import snake.tools.protocol.Content;
import snake.tools.protocol.Head;
import snake.tools.utils.BCDByteUtil;
import snake.tools.utils.ByteUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenxy on 2018/1/1.
 */
public class ConfigTools {


    public static String int2Code(int command) {
        byte[] bytes = ByteUtil.short2byte((short)command);
        return BCDByteUtil.hexString(bytes);
    }

    public static int code2Int(String code) {
        short command = Short.parseShort(code);
        return ByteUtil.byte2int(BCDByteUtil.number2bcd(command, (byte) 2));
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
        if (null != parameters) {
            Attribute[] attributes = new Attribute[parameters.size()];
            for (int i = 0; i < attributes.length; i++) {
                Parameter parameter = parameters.get(i);
                attributes[i] = new Attribute(parameter.getCode(), parameter.getType(), parameter.getLength(), parameter.getDecimal());
            }
            return attributes;
        }
        return null;
    }

    public static Body buildBody(Integer number) throws IOException {
        return buildBody(int2Code(number));
    }

    public static Body buildBody(String code) throws IOException {
        Command command = getCommand(code);
        if (null != command) {
            return new Body(buildAttributes(command.getParameters()), command.getType());
        }
        return null;

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
            Body body = new Body(buildAttributes(command.getParameters()), command.getType());
            int code = code2Int(command.getCode());
            Head head = new Head(0x0400, 0, code);
            return new Content(head, body);
        }
        return null;
    }

}
