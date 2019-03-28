package com.snake.tools.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snake.base.SocketTool;
import snake.tools.mina.config.Action;
import snake.tools.mina.config.Command;
import snake.tools.mina.config.Server;
import snake.tools.mina.protocol.ConfigTools;
import snake.tools.mina.protocol.Content;
import snake.tools.utils.BCDByteUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * [{version,serial,command,[length,{body:parameterList}]}check] //todo
 * Created by HP on 2017/12/27.
 */
public class Json2ConfigTests {

    public static Server config = null;

    @BeforeClass
    public static void setConfig() throws IOException {
        String fileName = "protocol";
        config = Server.build(fileName).buildParameters().buildCommands().buildActions();
    }

    @Ignore
    @Test
    public void showInterface() {
        List<Action> interfaceList = config.getActionList();
        Iterator<Action> iterator = interfaceList.iterator();
        while (iterator.hasNext()) {
            Action action = iterator.next();
            Object interfaceJson = JSONObject.toJSON(action);
            System.out.println(action.getRequest().getCode());
            System.out.println(interfaceJson);
        }
    }

    @Test
    public void checkCommands() throws IOException {
        Iterator<String> iterator = config.getCommandMap().keySet().iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            Command command = config.getCommandMap().get(code);
            JSONObject jsonObject = command.getDefaultJson();
            Content content = ConfigTools.buildContent(command);
            if (null != content) {
                System.out.println(String.format("code:%s ,name:%s", command.getCode(), command.getName()));
//            JSONObject json = new JSONObject();
//            json.put("version","0.2v20180105");
                System.out.println("default json : " + jsonObject);
                byte[] bytes = content.encode(jsonObject);
                System.out.println(BCDByteUtil.hexString(bytes));
                System.out.println("json value : " + Content.decode(bytes));
            }
        }
    }

    private static final String ERROR_CODE_KEY = "errcode";
    private static final String CODE_KEY = "code";

    @Test
    public void checkInterfaces() throws IOException {
        List<Action> actions = config.getActionList();
        Action[] actionArray = new Action[actions.size()];
        actions.toArray(actionArray);
        Arrays.sort(actionArray);
        Long userId = 0l;
        for (int i = 0; i < actionArray.length; i++) {
            if (actionArray[i].getIndex() < 0) {
                continue;
            }
            Command command = actionArray[i].getRequest();
            JSONObject defaultJson = command.getDefaultJson();
            Content content = ConfigTools.buildContent(command);
            if (null != content) {
                System.out.println(String.format("code:%s ,name:%s", command.getCode(), command.getName()));
//            JSONObject json = new JSONObject();
//            json.put("version","0.2v20180105");
                System.out.println("request default json : " + defaultJson);
                defaultJson.put("userId", userId);
                byte[] bytes = content.encode(defaultJson);
                System.out.println("request byte : " + BCDByteUtil.hexString(bytes));
                byte[] responseBytes = SocketTool.request(bytes);
                System.out.println("response byte : " + BCDByteUtil.hexString(responseBytes));
                assert null != (content = Content.decode(responseBytes));
                if (null != content.getBody()) {
                    JSONArray array = content.getBody().getJsonArray();
                    assert null != array;
                    JSONObject json = array.getJSONObject(0);
                    assert (null != json);
                    System.out.println("response json value : " + json);
                    if (null != json.get(ERROR_CODE_KEY)) {
                        assert json.getIntValue(ERROR_CODE_KEY) == 0;
                    } else if (null != json.get(CODE_KEY)) {
                        assert json.getIntValue(CODE_KEY) == 0;
                        if (null != json.get("uid")) {
                            userId = json.getLong("uid");
                        }
                    } else {
                        System.out.println("no data return !!!");
                    }
                } else {
                    System.out.println("error happened !!!");
                }
            }
        }
    }

}
