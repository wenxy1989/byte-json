package snake.tools.config;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by wenxy on 2017/12/31.
 */
public class Command {

    public enum Type {
        type_json, type_byte;

        public static Type getType(String type) {
            if (null == type) {
                return type_byte;
            } else if ("json".equals(type.toLowerCase())) {
                return type_json;
            } else if ("byte".equals(type)) {
                return type_byte;
            }
            return type_byte;
        }
    }

    private String code;
    private String name;
    private Type type;
    private List<Parameter> parameters;

    private JSONObject defaultJson;

    private JSONObject buildJsonValue(List<Parameter> parameters) {
        if (null != parameters && parameters.size() > 0) {
            JSONObject json = new JSONObject();
            for (int i = 0; i < parameters.size(); i++) {
                Parameter parameter = parameters.get(i);
                json.put(parameter.getCode(), parameter.getDefaultValue());
            }
            return json;
        }
        return null;
    }

    public JSONObject getDefaultJson() {
        if (null == defaultJson) {
            defaultJson = buildJsonValue(this.parameters);
        }
        return defaultJson;
    }

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
        this.type = Type.getType(type);
    }

    public void setType(Type type) {
        this.type = type;
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

    public Type getType() {
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
