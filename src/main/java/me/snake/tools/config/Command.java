package me.snake.tools.config;

import java.util.List;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Command {

    public static final String command_type_json = "json";
    public static final String command_type_byte = "byte";
    private String code;
    private String name;
    private String type;
    private List<Parameter> parameters;

    public void setCode(String code) {
        if (null != code && code.trim().length() > 0) {
            this.code = code;
        }
    }

    public void setName(String name) {
        if (null != name && name.trim().length() > 0) {
            this.name = name;
        }
    }

    public void setType(String type) {
        if (null != type && type.trim().length() > 0) {
            this.type = type;
        }
    }

    public void setParameters(List<Parameter> parameters) {
        if (null != parameters && parameters.size() > 0) {
            this.parameters = parameters;
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Command copy() {
        Command obj = new Command();
        obj.setName(name);
        obj.setCode(code);
        obj.setType(type);
        obj.setParameters(parameters);
        return obj;
    }
}
