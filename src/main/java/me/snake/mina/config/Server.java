package me.snake.mina.config;

import com.alibaba.fastjson.JSONObject;
import me.snake.mina.json.JsonArray;
import me.snake.mina.json.JsonObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/12/27.
 */
public class Server {

    private String address;
    private int port;
    private String version;
    private Map<String, Parameter> parameterMap;
    private Map<String, Command> commandMap;
    private Map<String, Action> interfaceMap;

    private JsonObject jsonConfig;

    private Server(Map<String, Object> config) {
        jsonConfig = new JsonObject(config);
        if (!jsonConfig.isEmpty()) {
            address = jsonConfig.getString("address");
            port = jsonConfig.getInteger("port");
            version = jsonConfig.getString("version");
        } else {

        }
    }

    public Server buildParameters() {
        List<Parameter> list = buildParameters(jsonConfig.getJsonArray("parameters"), this.parameterMap);
        if (null != list && list.size() > 0) {
            this.parameterMap = new HashMap<String, Parameter>();
            for (Parameter each : list) {
                this.parameterMap.put(each.getCode(), each);
            }
        }
        return this;
    }

    public Server buildCommands() {
        List<Command> list = buildCommands(jsonConfig.getJsonArray("commands"), this.commandMap, this.parameterMap);
        if (null != list && list.size() > 0) {
            this.commandMap = new HashMap<String, Command>();
            for (Command each : list) {
                this.commandMap.put(each.getCode(), each);
            }
        }
        return this;
    }

    public Server buildInterfaces() {
        this.interfaceMap = buildInterfaces(jsonConfig.getJsonObject("interfaces"), this.commandMap);
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Action> getInterfaceMap() {
        return interfaceMap;
    }

    public void setInterfaceMap(Map<String, Action> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }

    public Map<String, Parameter> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Parameter> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public static Server build(Map<String, Object> config) {
        Server server = new Server(config);
//            buildParameters(json.getJsonArray("parameters"));
//            buildInterfaces(json.getJsonObject("interfaces"));
        return server;
    }

    public static List<Command> buildCommands(JsonArray array, Map<String, Command> commandMap, Map<String, Parameter> parameterMap) {
        List<Command> commands = null;
        if (null != array && !array.isEmpty()) {
            commands = new ArrayList<Command>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject each = array.getJsonObject(i);
                Command parameter = buildCommand(each, commandMap, parameterMap);
                commands.add(parameter);
            }
        }
        return commands;
    }

    public static List<Parameter> buildParameters(JsonArray array, Map<String, Parameter> parameterMap) {
        List<Parameter> parameters = null;
        if (null != array && !array.isEmpty()) {
            parameters = new ArrayList<Parameter>();
            for (int i = 0; i < array.size(); i++) {
                JsonObject each = array.getJsonObject(i);
                Parameter parameter = buildParameter(each, parameterMap);
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    public static Parameter buildParameter(JsonObject json, Map<String, Parameter> parameterMap) {
        Parameter parameter = null;
        String key = json.getString("copy");
        if (null != key && null != parameterMap && !parameterMap.isEmpty()) {
            parameter = parameterMap.get(key) == null ? null : parameterMap.get(key).copy();
        }
        if (null == parameter) {
            parameter = new Parameter();
        }
        parameter.setCode(json.getString("code"));
        parameter.setName(json.getString("name"));
        parameter.setJavaType(json.getString("javaType"));
        parameter.setByteType(json.getString("byteType"));
        parameter.setByteLength(json.getInteger("byteLength"));
        parameter.setDefaultValue(json.getString("defaultValue"));
        return parameter;
    }

    public static Command buildCommand(JsonObject json, Map<String, Command> commandMap, Map<String, Parameter> parameterMap) {
        Command command = null;
        String key = json.getString("parameter");
        if (null != key && null != commandMap && !commandMap.isEmpty()) {
            command = commandMap.get(key) == null ? null : commandMap.get(key).copy();
        }
        if (null == command) {
            command = new Command();
        }
        command.setCode(json.getString("code"));
        command.setName(json.getString("name"));
        command.setType(json.getString("type"));
        List<Parameter> parameters = buildParameters(json.getJsonArray("parameters"), parameterMap);
        command.setParameters(parameters);
        return command;
    }

    public static Map<String, Action> buildInterfaces(JsonObject json, Map<String, Command> commandMap) {
        Map<String, Action> interfaces = null;
        if (null != json && !json.isEmpty()) {
            interfaces = new HashMap<String, Action>();
            for (String key : json.keySet()) {
                Action inter = new Action();
                inter.setRequest(commandMap.get(key));
                inter.setResponse(commandMap.get(json.getString(key)));
                interfaces.put(key, inter);
            }
        }
        return interfaces;
    }

    private static String configFileName = "protocol.json";

    public static void setConfigFileName(String fileName) {
        configFileName = fileName;
    }

    private static Server server;

    public static Server getInstance() throws IOException {
        if (null == server) {
            File file = new File(System.class.getResource("/").getPath(), configFileName);
            List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            String text = String.join("", list);
            JSONObject instance = JSONObject.parseObject(text);
            server = Server.build(instance).buildParameters().buildCommands().buildInterfaces();
        }
        return server;
    }

}
