package com.snake.config.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.base.SocketTool;
import snake.tools.mina.config.Command;
import snake.tools.mina.config.Parameter;
import snake.tools.mina.config.Server;
import snake.tools.mina.protocol.ConfigTools;
import snake.tools.mina.protocol.Content;
import snake.tools.utils.FileStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/5/22.
 */
public class CommandTests {

    private static Map<String,Parameter> parameterMap;
    private static Map<String,Command> commandMap;

    @BeforeClass
    public static void init() throws IOException {
        parameterMap = new HashMap<String, Parameter>();
        JSONObject sourceJson = FileStringUtils.readClassPathJSONObject("protocol/parameter");
        JSONArray parameterJsonArray = sourceJson.getJSONArray("protocol/parameter");
        Server.buildParameters(parameterJsonArray,parameterMap);
    }


    @Test
    public void buildTest() throws IOException {
        JSONArray jsonArray = FileStringUtils.readClassPathJSONArray("command-log");
        commandMap = new HashMap<String, Command>();
        List<Command> commands = Server.buildCommands(jsonArray, commandMap, parameterMap);
        for(Command command : commands) {
            Content content = ConfigTools.buildContent(command);
            byte[] bytes = content.encode(command.getDefaultJson());
            SocketTool.request(bytes);
            System.out.println(content.getBody().getJsonArray());
            System.out.println(bytes);
        }
    }

}
