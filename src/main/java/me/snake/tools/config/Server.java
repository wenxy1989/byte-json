package me.snake.tools.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by HP on 2017/12/27.
 */
public class Server {

    private String address;
    private int port;
    private String version;
    private Map<String, Parameter> parameterMap;
    private Map<String, Command> commandMap;
    private List<Action> actionList;

    private JSONObject jsonConfig;

    public static String readFile(String fileName) throws IOException {
        String classpath = System.class.getResource("/").getPath();
        File file = new File(classpath, fileName);
        List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        return String.join("", list);
    }

    public static JSONObject buildJSONObject(String jsonName) throws IOException {
        return JSONObject.parseObject(readFile(jsonName + ".json"));
    }

    public static JSONArray buildJSONArray(String jsonName) throws IOException {
        return JSONArray.parseArray(readFile(jsonName + ".json"));
    }

    private Server(String jsonName) throws IOException {
        jsonConfig = buildJSONObject(jsonName);
        if (!jsonConfig.isEmpty()) {
            address = jsonConfig.getString("address");
            port = jsonConfig.getInteger("port");
            version = jsonConfig.getString("version");
            buildJSONObjectImports(jsonConfig, jsonConfig.getJSONArray("import"));
            parameterMap = new HashMap<String, Parameter>();
            commandMap = new HashMap<String, Command>();
        } else {

        }
    }

    public static void buildJSONObjectImports(JSONObject json, JSONArray jsonList) throws IOException {
        if (jsonList != null && jsonList.size() > 0) {
            for (int i = 0; i < jsonList.size(); i++) {
                String name = (String) jsonList.get(i);
                buildJSONObjectImport(json, name);
            }
        }
    }

    public static void buildJSONObjectImport(JSONObject source, String jsonName) throws IOException {
        JSONObject importJSON = buildJSONObject(jsonName);
        if (!importJSON.isEmpty()) {
            Iterator<String> iterator = importJSON.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (source.containsKey(key) && null != source.getJSONArray(key) && null != importJSON.getJSONArray(key)) {
                    source.getJSONArray(key).addAll(importJSON.getJSONArray(key));
                } else {
                    source.put(key, importJSON.get(key));
                }
            }
        }
    }

    public static void buildJSONArrayImports(JSONArray source, JSONArray jsonList) throws IOException {
        if (jsonList != null && jsonList.size() > 0) {
            for (int i = 0; i < jsonList.size(); i++) {
                String name = (String) jsonList.get(i);
                buildJSONArrayImport(source, name);
            }
        }
    }

    public static void buildJSONArrayImport(JSONArray source, String jsonName) throws IOException {
        source.addAll(buildJSONArray(jsonName));
    }

    public static Server build(String jsonName) throws IOException {
        Server server = new Server(jsonName);
//            buildParameters(json.getJSONArray("parameters"));
//            buildInterfaces(json.getJSONObject("interfaces"));
        return server;
    }

    public Server buildParameters() throws IOException {
        JSONArray parameterArray = jsonConfig.getJSONArray("parameter");
        buildJSONArrayImports(parameterArray, jsonConfig.getJSONArray("parameter-import"));
        List<Parameter> list = buildParameters(parameterArray, this.parameterMap);
        /*if (null != list && list.size() > 0) {
            for (Parameter each : list) {
                this.parameterMap.put(each.getCode(), each);
            }
        }*/
        return this;
    }

    public Server buildCommands() throws IOException {
        JSONArray commandArray = jsonConfig.getJSONArray("command");
        buildJSONArrayImports(commandArray, jsonConfig.getJSONArray("command-import"));
        List<Command> list = buildCommands(commandArray, this.commandMap, this.parameterMap);
        /*if (null != list && list.size() > 0) {
            this.commandMap = new HashMap<String, Command>();
            for (Command each : list) {
                this.commandMap.put(each.getCode(), each);
            }
        }*/
        return this;
    }

    public Server buildActions() {
        actionList = buildActions(jsonConfig.getJSONArray("action"), this.commandMap);
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

    public List<Action> getActionList() {
        return actionList;
    }

    public Map<String, Parameter> getParameterMap() {
        return parameterMap;
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public static List<Command> buildCommands(JSONArray array, Map<String, Command> commandMap, Map<String, Parameter> parameterMap) {
        List<Command> commands = null;
        if (null != array && !array.isEmpty()) {
            commands = new ArrayList<Command>();
            for (int i = 0; i < array.size(); i++) {
                JSONObject each = array.getJSONObject(i);
                Command parameter = buildCommand(each, commandMap, parameterMap);
                commands.add(parameter);
            }
        }
        return commands;
    }

    public static List<Parameter> buildParameters(JSONArray array, Map<String, Parameter> parameterMap) {
        List<Parameter> parameters = null;
        if (null != array && !array.isEmpty()) {
            parameters = new ArrayList<Parameter>();
            for (int i = 0; i < array.size(); i++) {
                JSONObject each = array.getJSONObject(i);
                Parameter parameter = buildParameter(each, parameterMap);
                parameters.add(parameter);
            }
        }
        return parameters;
    }

    public static Parameter buildParameter(JSONObject json, Map<String, Parameter> parameterMap) {
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
        String javaType = parameter.getJavaType();
        if (Parameter.java_type_string.equals(javaType)) {
            parameter.setDefaultValue(json.getString("defaultValue"));
        } else if (Parameter.java_type_date.equals(javaType)) {
            parameter.setDefaultValue(json.getString("defaultValue"));
        } else if (Parameter.java_type_datetime.equals(javaType)) {
            parameter.setDefaultValue(json.getString("defaultValue"));
        } else if (Parameter.java_type_long.equals(javaType)) {
            parameter.setDefaultValue(json.getLong("defaultValue"));
        } else if (Parameter.java_type_integer.equals(javaType)) {
            parameter.setDefaultValue(json.getInteger("defaultValue"));
        } else if (Parameter.java_type_short.equals(javaType)) {
            parameter.setDefaultValue(json.getShort("defaultValue"));
        } else if (Parameter.java_type_byte.equals(javaType)) {
            parameter.setDefaultValue(json.getByte("defaultValue"));
        } else if (Parameter.java_type_double.equals(javaType)) {
            parameter.setDefaultValue(json.getDouble("defaultValue"));
        } else if (Parameter.java_type_float.equals(javaType)) {
            parameter.setDefaultValue(json.getFloat("defaultValue"));
        } else if (Parameter.java_type_char.equals(javaType)) {
            parameter.setDefaultValue(json.getString("defaultValue"));
        } else if (Parameter.java_type_boolean.equals(javaType)) {
            parameter.setDefaultValue(json.getBoolean("defaultValue"));
        } else {
            System.out.println(String.format("javaType : %s not define", parameter.getJavaType()));
        }
        if (null == parameter.getJavaType()) {
            System.out.println(String.format("parameter name:%s code:%s have no javaType", parameter.getName(), parameter.getCode()));
        }
        parameterMap.put(parameter.getCode(), parameter);
        return parameter;
    }

    public static Command buildCommand(JSONObject json, Map<String, Command> commandMap, Map<String, Parameter> parameterMap) {
        Command command = null;
        String key = json.getString("copy");
        if (null != key && null != commandMap && !commandMap.isEmpty()) {
            command = commandMap.get(key) == null ? null : commandMap.get(key).copy();
        }
        if (null == command) {
            command = new Command();
        }
        command.setCode(json.getString("code"));
        command.setName(json.getString("name"));
        command.setType(json.getString("type"));
        List<Parameter> parameters = buildParameters(json.getJSONArray("parameter"), parameterMap);
        command.setParameters(parameters);
        commandMap.put(command.getCode(), command);
        return command;
    }

    public static List<Action> buildActions(JSONArray array, Map<String, Command> commandMap) {

        if (null != array && !array.isEmpty()) {
            List<Action> interfaces = new ArrayList<Action>();
            for (int i = 0; i < array.size(); i++) {
                JSONObject actionJson = array.getJSONObject(i);
                Action inter = new Action();
                inter.setIndex(actionJson.getIntValue("index"));
                inter.setRequest(commandMap.get(actionJson.getString("request")));
                inter.setResponse(commandMap.get(actionJson.getString("response")));
                interfaces.add(inter);
            }
            return interfaces;
        }
        return null;
    }

    private static String configFileName = "protocol";

    public static void setConfigFileName(String fileName) {
        configFileName = fileName;
    }

    private static Server server;

    public static Server getInstance() throws IOException {
        if (null == server) {
            server = Server.build(configFileName).buildParameters().buildCommands().buildActions();
        }
        return server;
    }

}
