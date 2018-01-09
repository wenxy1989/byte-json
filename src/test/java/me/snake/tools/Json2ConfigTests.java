package me.snake.tools;

import com.alibaba.fastjson.JSONObject;
import me.snake.tools.config.Action;
import me.snake.tools.config.Command;
import me.snake.tools.config.Server;
import me.snake.tools.inter.ConfigTools;
import me.snake.tools.protocol.Content;
import me.snake.tools.utils.BCDByteUtil;
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
            Content content = ConfigTools.buildContent(command);
            if (null != content) {
                System.out.println(String.format("code:%s ,name:%s", command.getCode(), command.getName()));
//            JSONObject json = new JSONObject();
//            json.put("version","0.2v20180105");
                System.out.println("default json : " + content.getBody().getDefaultJson());
                byte[] bytes = content.encode();
                System.out.println(BCDByteUtil.hexString(bytes));
                if (null != (content = Content.decode(bytes))) {
                    System.out.println("json value : " + content.getBody().getJson());
                }
            }
        }
    }

    @Test
    public void checkIntefaces() throws IOException {
        List<Action> actions = config.getActionList();
        Action[] actionArray = new Action[actions.size()];
        actions.toArray(actionArray);
        Arrays.sort(actionArray);
        String userId = "00000000000";
        for (int i = 0; i < actionArray.length; i++) {
            if (actionArray[i].getIndex() < 0) {
                continue;
            }
            Command command = actionArray[i].getRequest();
            Content content = ConfigTools.buildContent(command);
            if (null != content) {
                System.out.println(String.format("code:%s ,name:%s", command.getCode(), command.getName()));
//            JSONObject json = new JSONObject();
//            json.put("version","0.2v20180105");
                JSONObject defaultJson = content.getBody().getDefaultJson();
                System.out.println("request default json : " + defaultJson);
                defaultJson.put("userId", userId);
                byte[] bytes = content.encode(defaultJson);
                System.out.println("request byte : " + BCDByteUtil.hexString(bytes));
                byte[] responseBytes = SocketTool.request(bytes);
                System.out.println("response byte : " + BCDByteUtil.hexString(responseBytes));
                assert null != (content = Content.decode(responseBytes));
                if (null != content.getBody()) {
                    JSONObject json = content.getBody().getJson();
                    if (null != json) {
                        System.out.println("response json value : " + json);
                        if (null != json.get("code")) {
                            assert json.getIntValue("code") == 0;
                        } else if (null != json.get("errcode")) {
                            assert json.getIntValue("errcode") == 0;
                            if (null != json.get("uid")) {
                                userId = formatNumber(json.getLong("uid").toString(), 11);
                            }
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

    private String formatNumber(String number, int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i >= length - number.length()) {
                sb.append(number.charAt(i - length + number.length()));
            } else {
                sb.append("0");
            }
        }
        return sb.toString();
    }

}
